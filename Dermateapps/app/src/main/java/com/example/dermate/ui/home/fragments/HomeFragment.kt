package com.example.dermate.ui.home.fragments

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dermate.databinding.FragmentHomeBinding
import com.example.dermate.ui.adapter.ArticleRecyclerAdapter
import com.example.dermate.viewmodel.ArticleViewModel

class HomeFragment : Fragment() {

    private lateinit var itemAdapter: ArticleRecyclerAdapter
    private lateinit var articleViewModel: ArticleViewModel
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        articleViewModel = ViewModelProvider(this)[ArticleViewModel::class.java]
        articleViewModel.articleData().observe(requireParentFragment().viewLifecycleOwner, {
            it.url?.let { data -> setUi(data.shuffled()) }
        })
    }

    private fun setUi(data: List<String>) {
        binding.apply {
            articleRv.layoutManager = LinearLayoutManager(context)
            itemAdapter = ArticleRecyclerAdapter(data)

            articleRv.adapter = itemAdapter
        }
    }

}