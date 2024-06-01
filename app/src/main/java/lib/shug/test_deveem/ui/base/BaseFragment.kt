package lib.shug.test_deveem.ui.base

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import lib.shug.test_deveem.model.utils.Resource

abstract class BaseFragment<VB : ViewBinding> : Fragment() {

    private var _binding: VB? = null
    protected val binding: VB
        get() = _binding!!

    abstract fun inflateBinding(inflater: LayoutInflater, container: ViewGroup?): VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        collectData()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflateBinding(inflater, container)
        return _binding!!.root
    }

    @SuppressLint("ResourceAsColor")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        initView()
        initListeners()
    }

    abstract fun observeData()

    abstract fun collectData()

    abstract fun initView()

    abstract fun initListeners()

    protected fun <T> observeResource(flow: Flow<Resource<T>>, onSuccess: (T) -> Unit) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                flow.collect { resource ->
                    when (resource) {
                        is Resource.Error -> {
                            Log.e("BaseFragment", "Error: ${resource.message}")
                        }
                        is Resource.Loading -> {
                            Log.d("BaseFragment", "Loading")
                        }
                        is Resource.Success -> {
                            resource.data?.let { onSuccess(it) }
                        }
                    }
                }
            }
        }
    }
}