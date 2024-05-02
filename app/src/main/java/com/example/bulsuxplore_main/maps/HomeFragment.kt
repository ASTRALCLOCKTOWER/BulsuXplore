package com.example.bulsuxplore_main.maps

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import com.example.bulsuxplore_main.R
import com.example.bulsuxplore_main.dashboard.DashAboutActivity
import com.example.bulsuxplore_main.dashboard.DashCalendarActivity
import com.example.bulsuxplore_main.dashboard.DashCollegesActivity
import com.example.bulsuxplore_main.dashboard.DashFaqsActivity
import com.example.bulsuxplore_main.dashboard.DashOrgActivity
import com.example.bulsuxplore_main.dashboard.DashPortalActivity


class HomeFragment : Fragment() {

    private lateinit var dashBulsu: CardView
    private lateinit var dashFaqs: CardView
    private lateinit var dashOrg: CardView
    private lateinit var dashColleges: CardView
    private lateinit var dashPortal: CardView
    private lateinit var dashCalendar:CardView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?


    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Initialize your views after inflating the layout
        dashBulsu = view.findViewById(R.id.dash_about_bulsu)
        dashFaqs = view.findViewById(R.id.dash_faqs)
        dashOrg = view.findViewById(R.id.dash_organization)
        dashColleges = view.findViewById(R.id.dash_colleges)
        dashPortal = view.findViewById(R.id.dash_portal)
        dashCalendar= view.findViewById(R.id.dash_calendar)

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dashBulsu.setOnClickListener{
            startActivity(Intent(requireContext(), DashAboutActivity::class.java))
        }

        dashFaqs.setOnClickListener{
            startActivity(Intent(requireContext(), DashFaqsActivity::class.java))
        }

        dashColleges.setOnClickListener{
            startActivity(Intent(requireContext(), DashCollegesActivity::class.java))
        }

        dashOrg.setOnClickListener{
            startActivity(Intent(requireContext(), DashOrgActivity::class.java))
        }


        dashPortal.setOnClickListener {
            startActivity(Intent(requireContext(), DashPortalActivity::class.java))

            }

        dashCalendar.setOnClickListener {
            startActivity(Intent(requireContext(), DashCalendarActivity::class.java))

        }
    }
}

// PAPALITAN TO AS ANNOUNCEMENT TAB SIGURO

