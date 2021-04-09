package com.cuvva.findyourdog.feature.list

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.cuvva.findyourdog.R
import com.cuvva.findyourdog.databinding.ActivityBreedListBinding
import com.cuvva.findyourdog.feature.detail.BreedDetailActivity
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class BreedListActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: BreedListViewModel
    private lateinit var binding: ActivityBreedListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory)[BreedListViewModel::class.java]
        binding = DataBindingUtil.setContentView(this, R.layout.activity_breed_list)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        initObserver()
    }

    private fun initObserver() {
        viewModel.event.observe(this, Observer {
            if (it is BreedListEvent.ShowBreedDetail) {
                showBreedDetails(it)
            }
        })
    }

    private fun showBreedDetails(event: BreedListEvent.ShowBreedDetail) {
        val intent = Intent(this, BreedDetailActivity::class.java)
        intent.putExtra("args_breed", event.breed)
        startActivity(intent)
    }

    override fun onStart() {
        super.onStart()
        viewModel.onStart()
    }
}