package com.hanium.floty.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.text.set
import androidx.core.text.toSpannable
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

import com.hanium.floty.R
import com.hanium.floty.decorator.LinearGradientSpan
import com.hanium.floty.model.User
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    lateinit var mReference: DatabaseReference
    lateinit var firebaseUser: FirebaseUser

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var view: View = inflater.inflate(R.layout.fragment_home, container, false)

        profileInfo()

        return view
    }

    private fun profileInfo() {
        firebaseUser = FirebaseAuth.getInstance().currentUser!!
        mReference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.uid)

        val postListener = object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user: User? = snapshot.getValue(User::class.java)

                user?.let {
                    dday.text = user.day
                    Glide.with(context!!).load(user.profile).into(profile)

                    // text에 gradient 넣는 코드..인데 안됨;;
                    val start = ContextCompat.getColor(context!!, R.color.colorBlue)
                    val end = ContextCompat.getColor(context!!, R.color.colorLightGreen)
                    var text = user.nickname.toString()
                    var spannable = text.toSpannable()
                    spannable[0..text.length]= LinearGradientSpan(text, text, start, end)
                    nickname.text = spannable.toString()
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        }
        mReference.addValueEventListener(postListener)
    }

}
