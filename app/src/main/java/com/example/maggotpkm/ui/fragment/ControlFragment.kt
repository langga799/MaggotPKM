package com.example.maggotpkm.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.maggotpkm.databinding.FragmentControlBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class ControlFragment : Fragment() {

    private lateinit var binding: FragmentControlBinding
    private val databaseReference = Firebase.database.reference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentControlBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val power = binding.btnPower


        databaseReference.child("control").child("alat")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    when (snapshot.value.toString()) {
                        "on" -> {
                            binding.btnPower.isChecked = true
                            binding.status.text = "Hidup"
                        }
                        "off" -> {
                            binding.btnPower.isChecked = false
                            binding.status.text = "Mati"
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d("ERROR", error.message)
                }

            })


        databaseReference.child("user").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Log.d("vvvvv", snapshot.value.toString() + FirebaseAuth.getInstance().uid)
                if (snapshot.hasChild(FirebaseAuth.getInstance().uid.toString())) {
                    Log.d("Harus Login", "Baru bisa akses")

                    power.setOnCheckedChangeListener { _, condition ->
                        when (condition) {
                            true -> {
                                databaseReference.child("control").child("alat")
                                    .setValue("on")
                                    .addOnSuccessListener {
                                        binding.status.text = "Hidup"
                                    }

                            }

                            false -> {
                                databaseReference.child("control").child("alat")
                                    .setValue("off")
                                    .addOnSuccessListener {
                                        binding.status.text = "Mati"
                                    }
                            }
                        }
                    }


                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })


    }


}