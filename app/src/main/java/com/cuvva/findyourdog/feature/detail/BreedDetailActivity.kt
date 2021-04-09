package com.cuvva.findyourdog.feature.detail

import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.cuvva.findyourdog.R
import com.cuvva.findyourdog.databinding.ActivityBreedDetailBinding
import com.cuvva.library_dogapi.domain.model.Breed
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class BreedDetailActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: BreedDetailViewModel
    private lateinit var binding: ActivityBreedDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory)[BreedDetailViewModel::class.java]
        binding = DataBindingUtil.setContentView(this, R.layout.activity_breed_detail)
        setSupportActionBar(binding.breedDetailToolbar)
        supportActionBar?.apply { setDisplayHomeAsUpEnabled(true) }
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val breed = intent.getParcelableExtra("args_breed") as Breed?
        viewModel.setArgs(breed)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> false
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.onStart()
    }
}