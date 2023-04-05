package com.example.swiftyproteins.presentation.fragments

import android.content.Intent
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import com.example.swiftyproteins.R
import com.example.swiftyproteins.databinding.FragmentProteinViewBinding
import com.example.swiftyproteins.presentation.dialog.DialogCreator
import com.example.swiftyproteins.presentation.fragments.base.BaseScreenStateFragment
import com.example.swiftyproteins.presentation.models.Protein
import com.example.swiftyproteins.presentation.models.ProteinError
import com.example.swiftyproteins.presentation.models.State
import com.example.swiftyproteins.presentation.navigation.FromProtein
import com.example.swiftyproteins.presentation.scene.SceneRender
import com.example.swiftyproteins.presentation.viewmodels.ProteinViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.swiftyproteins.presentation.models.ModelAtomInfo
import android.net.Uri
import android.view.*
import com.example.swiftyproteins.presentation.*
import com.example.swiftyproteins.presentation.navigation.Screens
import eu.bolt.screenshotty.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ProteinFragment : BaseScreenStateFragment<FromProtein, Protein, ProteinViewModel>() {

    private var binding: FragmentProteinViewBinding? = null
    private var sceneRender: SceneRender? = null
    private var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>? = null

    private val proteinName: String? by lazy {
        arguments?.getString(ARG_PROTEIN_NAME)
    }
    override val viewModel: ProteinViewModel by viewModel {
        parametersOf(proteinName)
    }

    private val screenshotManager by lazy {
        ScreenshotManagerBuilder(requireActivity())
            .withPermissionRequestCode(REQUEST_SCREENSHOT_PERMISSION)
            .withCustomActionOrder(ScreenshotActionOrder.pixelCopyFirst())
            .build()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProteinViewBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initScene()
        binding?.toolbar?.title = proteinName
        setupView()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        screenshotManager.onActivityResult(requestCode, resultCode, data)
        sceneRender
    }

    private fun setupView() {
        binding?.toolbar?.setNavigationOnClickListener {
            viewModel.onBackClick()
        }
        binding?.bottomSheet?.bottomSheet?.let { bottomSheet ->
            bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        }
        binding?.imgShare?.setOnClickListener {
            viewModel.onImageShareClick()
        }
        binding?.imgHydrogen?.setOnClickListener { img ->
            viewModel.onImageHydrogenClick(img.isActivated)
        }
    }

    override fun setupObserve() {
        super.setupObserve()
        viewModel.modelAtomInfo.observe(viewLifecycleOwner, ::handleAtomInfo)
    }

    private fun handleAtomInfo(model: ModelAtomInfo) {
        binding?.bottomSheet?.tvAtomMass?.text = model.atomInfo.atomicMass
        binding?.bottomSheet?.layoutBoiling?.isVisible = model.isBoilingPointVisible
        binding?.bottomSheet?.tvBoilingPoint?.text =
            getString(R.string.degree_celsius, model.atomInfo.boil)
        binding?.bottomSheet?.layoutMeltingPoint?.isVisible = model.isMeltingPointVisible
        binding?.bottomSheet?.tvMeltingPoint?.text =
            getString(R.string.degree_celsius, model.atomInfo.melt)
        binding?.bottomSheet?.tvName?.text = model.atomFullName
        binding?.bottomSheet?.tvSummary?.text = model.atomInfo.summary
    }

    private fun initScene() {
        sceneRender = SceneRender()
        initSceneRender()
    }

    private fun initSceneRender() {
        binding?.sceneView?.let { sceneView ->
            sceneRender
                ?.initSceneView(sceneView)
                ?.setBackground(getColor(R.color.color_background_scene))
                ?.setOnNodeTouchListener(requireContext(), viewModel::onNodeTouch)
                ?.setDisplayMetrics(resources.displayMetrics)
        }
    }

    override fun handleModel(model: Protein) {
        initSceneRender()
        model.atoms.forEach { atom ->
            sceneRender?.setSphere(
                requireContext(),
                atom.name,
                atom.coordinate,
                getColor(atom.colorResId)
            )
        }
        model.atomConnections.forEach { atomConnection ->
            sceneRender?.setCylinder(
                requireContext(),
                atomConnection.coordinateTop,
                atomConnection.coordinateBottom,
                getColor(atomConnection.colorResId)
            )
        }
    }

    override fun handleAction(action: FromProtein) {
        when (action) {
            is FromProtein.Navigate.Back ->
                router.exit()
            is FromProtein.BackTo.Login ->
                router.backTo(Screens.Login)
            is FromProtein.Command.ShowNotFoundErrorDialog ->
                showNotFoundDialog(action.error)
            is FromProtein.Command.ShowNetworkErrorDialog ->
                showNetworkErrorDialog(action.error)
            is FromProtein.Command.ShowBottomSheet ->
                showBottomSheet()
            is FromProtein.Command.HideBottomSheet ->
                hideBottomSheet()
            is FromProtein.Command.MakeSceneScreenBitmap ->
                makeSceneScreenBitmap()
            is FromProtein.Command.MakeUiScreenBitmap ->
                makeUiScreenBitmap()
            is FromProtein.Command.ShareScreenByUri ->
                shareImage(action.uri)
            is FromProtein.Command.ChangeImageHydrogenActivate ->
                binding?.imgHydrogen?.isActivated = action.isActivated
            is FromProtein.Command.ClearScene ->
                sceneRender?.cleanScene()
        }
    }

    private fun makeSceneScreenBitmap() {
        sceneRender?.getCurrentScene()?.let { sceneView ->
            getBitmapFromView(sceneView, viewModel::onSceneBitmapReady)
        }
    }

    private fun makeUiScreenBitmap() {
        screenshotManager.makeScreenshot().observe(
            onSuccess = viewModel::onUiScreenReady,
            onError = { t ->
                logE(t.message, t as Exception)
            }
        )
    }

    private fun shareImage(uri: Uri) {
        ChooserShareScreen.start(this, uri)
    }

    private fun showBottomSheet() {
        binding?.bottomSheet?.bottomSheet?.isVisible = true
        binding?.root?.postDelayed({
            bottomSheetBehavior?.state = BottomSheetBehavior.STATE_COLLAPSED
        }, 500)
    }

    private fun hideBottomSheet() {
        bottomSheetBehavior?.state = BottomSheetBehavior.STATE_HIDDEN
    }

    private fun showNotFoundDialog(error: ProteinError) {
        DialogCreator().showNotFoundErrorDialog(
            requireContext(),
            error,
            viewModel::onNotFoundDialogCancelable
        )
    }

    private fun showNetworkErrorDialog(error: ProteinError) {
        DialogCreator().showNetworkErrorDialog(
            requireContext(),
            error,
            viewModel::onNetworkErrorDialogCancelable,
            viewModel::onNetworkErrorDialogRetryClick
        )
    }

    override fun handleState(state: State) {
        when (state) {
            is State.Loading -> binding?.progressBar?.isVisible = true
            is State.Success -> binding?.progressBar?.isVisible = false
        }
    }

    override fun onPause() {
        super.onPause()
        sceneRender?.onPause()
    }

    override fun onResume() {
        super.onResume()
        sceneRender?.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        sceneRender?.onDestroy()
        binding = null
    }

    companion object {
        private const val REQUEST_SCREENSHOT_PERMISSION = 888
        private const val ARG_PROTEIN_NAME = "protein_name"

        fun newInstance(proteinName: String) =
            ProteinFragment().apply {
                arguments = bundleOf(ARG_PROTEIN_NAME to proteinName)
            }
    }
}
