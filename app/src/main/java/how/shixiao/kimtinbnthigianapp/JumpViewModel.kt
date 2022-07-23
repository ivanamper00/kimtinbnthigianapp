package how.shixiao.kimtinbnthigianapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import how.shixiao.kimtinbnthigianapp.retrofit.JumpServiceImp
import how.shixiao.kimtinbnthigianapp.retrofit.RequestModel
import how.shixiao.kimtinbnthigianapp.retrofit.ResponseModel
import how.shixiao.kimtinbnthigianapp.utils.UiState

class JumpViewModel: ViewModel() {

    private val repo = JumpServiceImp()

    private val _urlResponse = MutableLiveData<UiState<ResponseModel>>()
    val urlResponse : LiveData<UiState<ResponseModel>>
        get() = _urlResponse

    fun getJumpUrl(packageName: String){
        val param = RequestModel(
            packageName
        )
        viewModelScope.launch {
            repo.getJumpCodeUrl(param)
                .catch { err -> _urlResponse.value = UiState.Error(err) }
                .collectLatest {
                    _urlResponse.value = UiState.Success(it)
                }
        }
    }
}