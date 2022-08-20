package com.example.maggotpkm.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.maggotpkm.R
import com.example.maggotpkm.databinding.FragmentMonitoringBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class MonitoringFragment : Fragment() {

    private lateinit var binding: FragmentMonitoringBinding
    private val databaseReference = Firebase.database.reference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMonitoringBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        databaseReference.child("monitoring").child("suhu")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.value.toString().apply {
                        binding.circularProgressBarTemp.progress = this.toFloat()
                        binding.tvDataTemp.text = this
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(requireActivity(), error.message, Toast.LENGTH_SHORT).show()
                }

            })


        databaseReference.child("monitoring").child("kelembaban")
            .addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.value.let {
                        binding.circularProgressBarHumi.progress = it.toString().toFloat()
                        binding.tvDataHumidity.text = it.toString()
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })


        databaseReference.child("monitoring").child("berat")
            .addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.value.toString().let { data ->
                        binding.apply {
                            circularProgressBarWeight.progress = data.toFloat()
                            tvDataWeight.text = data
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })

    }

}