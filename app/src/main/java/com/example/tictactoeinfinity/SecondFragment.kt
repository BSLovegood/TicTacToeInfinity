package com.example.tictactoeinfinity

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.tictactoeinfinity.databinding.FragmentSecondBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    val viewModel: MainViewModel by viewModels()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        setupBoard()

        viewModel.getShowDialogLiveData().observe(viewLifecycleOwner) { message ->
            if (message.isEmpty())
                return@observe

            showWinDialog(message)
        }

        viewModel.getButtonsLiveData().observe(viewLifecycleOwner) {

            binding.button00.setImageResource(it[0][0].imageRes)
            binding.button00.alpha = it[0][0].alpha

            binding.button01.setImageResource(it[0][1].imageRes)
            binding.button01.alpha = it[0][1].alpha

            binding.button02.setImageResource(it[0][2].imageRes)
            binding.button02.alpha = it[0][2].alpha


            binding.button10.setImageResource(it[1][0].imageRes)
            binding.button10.alpha = it[1][0].alpha

            binding.button11.setImageResource(it[1][1].imageRes)
            binding.button11.alpha = it[1][1].alpha

            binding.button12.setImageResource(it[1][2].imageRes)
            binding.button12.alpha = it[1][2].alpha


            binding.button20.setImageResource(it[2][0].imageRes)
            binding.button20.alpha = it[2][0].alpha

            binding.button21.setImageResource(it[2][1].imageRes)
            binding.button21.alpha = it[2][1].alpha

            binding.button22.setImageResource(it[2][2].imageRes)
            binding.button22.alpha = it[2][2].alpha
        }

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonRestart.setOnClickListener {
            viewModel.resetBoard()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun showWinDialog(winner: String) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Game Over")
        builder.setMessage("$winner wins")
        builder.setIcon(R.drawable.icon_tictactoe)
        builder.setPositiveButton("Restart") { dialog, _ ->
            dialog.dismiss()
            viewModel.dissmissed()
        }
        val dialog = builder.create()
        dialog.show()
    }

    private fun setupBoard() {

        binding.button00.setOnClickListener {
            viewModel.onCellClicked(0, 0)
        }
        binding.button01.setOnClickListener {
            viewModel.onCellClicked(0, 1)
        }
        binding.button02.setOnClickListener {
            viewModel.onCellClicked(0, 2)
        }
        binding.button10.setOnClickListener {
            viewModel.onCellClicked(1, 0)
        }
        binding.button11.setOnClickListener {
            viewModel.onCellClicked(1, 1)
        }
        binding.button12.setOnClickListener {
            viewModel.onCellClicked(1, 2)
        }
        binding.button20.setOnClickListener {
            viewModel.onCellClicked(2, 0)
        }
        binding.button21.setOnClickListener {
            viewModel.onCellClicked(2, 1)
        }
        binding.button22.setOnClickListener {
            viewModel.onCellClicked(2, 2)
        }
    }
}