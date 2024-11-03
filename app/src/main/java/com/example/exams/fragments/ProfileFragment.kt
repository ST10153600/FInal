package com.example.exams

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class ProfileFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var profileImageView: ImageView
    private lateinit var nameTextView: TextView
    private lateinit var emailTextView: TextView
    private lateinit var logoutButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        auth = FirebaseAuth.getInstance()

        profileImageView = view.findViewById(R.id.profileImageView)
        nameTextView = view.findViewById(R.id.nameTextView)
        emailTextView = view.findViewById(R.id.emailTextView)
        logoutButton = view.findViewById(R.id.logoutButton)

        loadUserInfo()

        logoutButton.setOnClickListener {
            auth.signOut()
            startActivity(Intent(activity, Login::class.java))
            activity?.finish()
        }

        return view
    }

    private fun loadUserInfo() {
        val user: FirebaseUser? = auth.currentUser
        if (user != null) {
            nameTextView.text = user.displayName ?: "Anonymous"
            emailTextView.text = user.email ?: "Email not available"

            val googleProfilePicUrl = user.photoUrl
            if (googleProfilePicUrl != null) {
                // Load Google profile picture if available
                Glide.with(this).load(googleProfilePicUrl).into(profileImageView)
            } else {
                // Generate default avatar based on username
                val defaultAvatarUrl = "https://avatar.iran.liara.run/public/boy?username=${user.displayName ?: "user"}"

                // Load the avatar image
                Glide.with(this)
                    .load(defaultAvatarUrl)
                    .placeholder(R.drawable.profile_placeholder)
                    .into(profileImageView)
            }
        } else {
            Toast.makeText(activity, "User not logged in", Toast.LENGTH_SHORT).show()
        }
    }

}