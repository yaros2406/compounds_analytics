package com.example.swiftyproteins.presentation.viewmodels

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import com.example.swiftyproteins.data.model.AtomsInfo
import com.example.swiftyproteins.data.model.ErrorType
import com.example.swiftyproteins.domain.interactor.ProteinInteractor
import com.example.swiftyproteins.presentation.logE
import com.example.swiftyproteins.presentation.mapper.ProteinMapper
import com.example.swiftyproteins.presentation.models.*
import com.example.swiftyproteins.presentation.navigation.FromProtein
import com.example.swiftyproteins.presentation.overlayToCenter
import com.example.swiftyproteins.presentation.view.controller.SingleEvent
import com.example.swiftyproteins.presentation.viewmodels.base.BaseScreenStateViewModel
import com.google.ar.sceneform.Node
import eu.bolt.screenshotty.Screenshot
import eu.bolt.screenshotty.ScreenshotBitmap
import java.lang.IllegalStateException

class ProteinViewModel(
    private val proteinName: String?,
    private val interactor: ProteinInteractor,
    private val mapper: ProteinMapper
) : BaseScreenStateViewModel<FromProtein, Protein>(Protein()) {

    private var sceneBitmap: Bitmap? = null
    var modelAtomInfo: MutableLiveData<ModelAtomInfo> = SingleEvent()

    private fun updateModel(atoms: Protein) {
        model = atoms
        handleModel()
    }

    init {
       init()
    }

    private fun init() {
        proteinName?.let { name ->
            getProteins(name)
        } ?: logE("missing protein name", IllegalStateException())
    }

    private fun getProteins(proteinName: String, isHyAtomsVisible: Boolean = false) {
        handleState(State.Loading)
        interactor.getProteinByName(proteinName,
            onSuccess = { ligand ->
                val atoms: Protein = mapper.map(ligand, isHyAtomsVisible)
                handleState(State.Success)
                updateModel(atoms)
            },
            onError = { errorType ->
                handleState(State.Success)
                onError(errorType)
            })
    }

    private fun onError(errorType: ErrorType) {
        when (errorType) {
            ErrorType.NotFound -> handleAction(
                FromProtein.Command.ShowNotFoundErrorDialog(
                    ProteinError.ProteinNotFound
                )
            )
            ErrorType.Network -> handleAction(
                FromProtein.Command.ShowNetworkErrorDialog(
                    ProteinError.NetworkError
                )
            )
            ErrorType.Unknown -> handleAction(
                FromProtein.Command.ShowNetworkErrorDialog(
                    ProteinError.NetworkError as ProteinError
                )
            )
        }
    }

    fun onImageShareClick() =
        handleAction(FromProtein.Command.MakeSceneScreenBitmap)

    fun onImageHydrogenClick(isActivated: Boolean) {
        proteinName?.let { name ->
            handleAction(FromProtein.Command.ChangeImageHydrogenActivate(!isActivated))
            handleAction(FromProtein.Command.ClearScene)
            getProteins(name, !isActivated)
        }
    }

    fun onSceneBitmapReady(bitmap: Bitmap) {
        sceneBitmap = bitmap
        handleAction(FromProtein.Command.MakeUiScreenBitmap)
    }

    fun onUiScreenReady(screenshot: Screenshot) {
        val bitmapUi: Bitmap =
            when (screenshot) {
                is ScreenshotBitmap -> screenshot.bitmap
            }

        sceneBitmap?.overlayToCenter(bitmapUi)?.let { bitmap ->
            val uri = interactor.saveBitmapToCache(bitmap)
            handleAction(FromProtein.Command.ShareScreenByUri(uri))
        } ?: logE("scene screenshot missing")
    }

    fun onNotFoundDialogCancelable() =
        onBackClick()

    fun onNetworkErrorDialogCancelable() =
        onBackClick()

    fun onNetworkErrorDialogRetryClick() =
        init()

    fun onNodeTouch(node: Node) {
        node.name?.let { name ->
            handleAction(FromProtein.Command.HideBottomSheet)
            interactor.getAtomInfoByBaseName(mapper.mapAtomName(name))?.let { atomInfo ->
                showAtomInfo(atomInfo)
            } ?: "information about $name not found"
        }
    }

    private fun showAtomInfo(atomInfo: AtomsInfo.AtomInfo) {
        this.modelAtomInfo.value = ModelAtomInfo(atomInfo)
        handleAction(FromProtein.Command.ShowBottomSheet)
    }

    fun onBackClick() =
        handleAction(FromProtein.Navigate.Back)
}