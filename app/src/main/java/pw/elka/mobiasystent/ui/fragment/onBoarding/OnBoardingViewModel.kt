package pw.elka.mobiasystent.ui.fragment.onBoarding

import android.app.Application
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.viewpager2.widget.ViewPager2
import pw.elka.mobiasystent.R

import pw.elka.mobiasystent.model.SlideContent


class OnBoardingViewModel(application: Application) : AndroidViewModel(application) {
    private val list = listOf(
        SlideContent(
            ContextCompat.getDrawable(application.applicationContext, R.drawable.ic_people)!!,
            "Witaj",
            "Dzięki tej aplikacji będziesz mógł zadbać o najbliższych"
        ),

        SlideContent(
            ContextCompat.getDrawable(application.applicationContext, R.drawable.ic_save_time)!!,
            "Oszczędność czasu",
            "Dzięki aplikacji czeka Cie oszczędność czasu"
        )
    )

    private val _dataSet = MutableLiveData<List<SlideContent>>().apply { value = list }
    val dataSet: LiveData<List<SlideContent>>
        get() = _dataSet

    private val _buttonVisiability = MutableLiveData<Boolean>().apply { value = false }
    val buttonVisiability: LiveData<Boolean>
        get() = _buttonVisiability

    val pagerCallBack = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            _buttonVisiability.value = position == list.size - 1
            super.onPageSelected(position)
        }
    }
    private val _startNavigation = MutableLiveData<Boolean>().apply { value = false }
    val startNavigation: LiveData<Boolean>
        get() = _startNavigation

    fun navigateToAuth() {
        _startNavigation.value = true
    }

    fun doneNavigation() {
        _startNavigation.value = false
    }
}