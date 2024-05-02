package com.example.bulsuxplore_main.maps
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bulsuxplore_main.R
import com.example.bulsuxplore_main.databinding.FragmentEventsBinding
import com.example.bulsuxplore_main.maps.adapters.EventsAdapter
import com.example.bulsuxplore_main.users.EventData
import com.example.bulsuxplore_main.users.EventViewModel
import com.example.bulsuxplore_main.users.UserData
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

private lateinit var viewModel: EventViewModel
private lateinit var eventsRecyclerView: RecyclerView
lateinit var adapter: EventsAdapter

class EventsFragment : Fragment() {


    private lateinit var fab_button: FloatingActionButton
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var eventList:ArrayList<EventData>
    private lateinit var currentUser: FirebaseUser


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentEventsBinding.inflate(inflater, container, false)
        return binding.root


        firebaseAuth = FirebaseAuth.getInstance()

        fab_button = binding.root.findViewById(R.id.fab)



        //if editor; display FAB
        firebaseAuth.addAuthStateListener { firebaseAuth ->
            val user = firebaseAuth.currentUser

            if (user != null && user.email?.startsWith("org.") == true) {
                fab_button.visibility = View.VISIBLE

            } else {
                fab_button.visibility = View.GONE
            }
        }
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fab_button= view.findViewById(R.id.fab) //tanggalin if normal bulsuan
        fab_button.setOnClickListener{
            requireActivity().run {
                startActivity(Intent(this, PostActivity::class.java))
                finish() // If activity no more needed in back stack
            }

        }

        eventList  = ArrayList()
        eventsRecyclerView = view.findViewById(R.id.recyclerViewfeed)
        eventsRecyclerView.layoutManager = LinearLayoutManager(context)
        eventsRecyclerView.setHasFixedSize(true)
        adapter = EventsAdapter(requireContext(),eventList)
        eventsRecyclerView.adapter=adapter

        viewModel = ViewModelProvider(this).get(EventViewModel::class.java)

        viewModel.allEventData.observe(viewLifecycleOwner, Observer{
            adapter.updateEventList(it)
        })




    }



}




//        val url = "https://bulsu.priisms.online/auth/login"
//        val intent = CustomTabsIntent.Builder().build()
//        intent.launchUrl(requireContext(), Uri.parse(url))