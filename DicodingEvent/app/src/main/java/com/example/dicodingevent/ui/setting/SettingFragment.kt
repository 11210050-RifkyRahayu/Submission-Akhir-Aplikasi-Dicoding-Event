package com.example.dicodingevent.ui.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.dicodingevent.databinding.FragmentSettingBinding


class SettingFragment : Fragment() {
    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val pref = SettingPreferences.getInstance(requireContext().dataStore)
        val SettingViewModel = ViewModelProvider(this, ViewModelFactory(pref)).get(SettingViewModel::class.java)
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        val root: View = binding.root

        SettingViewModel.getThemeSettings().observe(viewLifecycleOwner) { isChecked: Boolean ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.switchTheme.isChecked = true
            }else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.switchTheme.isChecked = false
            }

            SettingViewModel.saveThemeSetting(isChecked)
        }
        binding.switchTheme.setOnCheckedChangeListener{ _: CompoundButton?, isChecked: Boolean ->
            SettingViewModel.saveThemeSetting(isChecked)
        }

        return root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}