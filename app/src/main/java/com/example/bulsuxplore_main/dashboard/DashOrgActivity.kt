package com.example.bulsuxplore_main.dashboard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bulsuxplore_main.R
import com.example.bulsuxplore_main.maps.adapters.BuildingsListAdapter
import com.example.bulsuxplore_main.maps.adapters.OrgListAdapter
import com.example.bulsuxplore_main.maps.data.Buildings
import com.example.bulsuxplore_main.maps.data.Orgs

class DashOrgActivity : AppCompatActivity() {
    private lateinit var newRecyclerView: RecyclerView
    private lateinit var newArrayList: ArrayList<Orgs>
    lateinit var orgImages: Array<Int>
    lateinit var orgGroups: Array<String>
    lateinit var orgDescs: Array<String>
    lateinit var orgLists: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash_org)

        orgImages = arrayOf(
            R.drawable.bldg_1,
            R.drawable.bldg_2,
            R.drawable.bldg_3,
            R.drawable.bldg_4,
            R.drawable.bldg_5,
            R.drawable.bldg_6,
            R.drawable.bldg_7,
            R.drawable.bldg_8,
            R.drawable.bldg_9,
            R.drawable.bldg_10,
            R.drawable.bldg_11,
            R.drawable.bldg_12,
            R.drawable.bldg_13,
            R.drawable.bldg_14,
            R.drawable.bldg_15,
            R.drawable.bldg_16,
            R.drawable.bldg_17,
        )

        orgGroups = arrayOf(
            "Academic group",
            "Fraternities",
            "Lifestyle groups",
            "Political groups",
            "Socio-civic",
            "Spiritual and religious groups",
            "College of Architecture and Fine Arts (CAFA)",
            "College of Arts and Letters (CAL)",
            "College of Business Administration (CBA)",
            "College of Criminal Justice Education (CCJE)",
            "College of Education (COED)",
            "College of Engineering (COE)",
            "College of Hospitality and Tourism Management (CHTM)",
            "College of Industrial Technology (CIT)",
            "College of Information and Communications Technology (CICT)",
            "College of Nursing (CON)",
            "College of Sports, Exercise and Recreation (CSER)"
        )

        orgDescs = arrayOf(
            getString(R.string.orgdesc_1),
            getString(R.string.orgdesc_2),
            getString(R.string.orgdesc_3),
            getString(R.string.orgdesc_4),
            getString(R.string.orgdesc_5),
            getString(R.string.orgdesc_6),
            getString(R.string.orgdesc_7),
            getString(R.string.orgdesc_8),
            getString(R.string.orgdesc_9),
            getString(R.string.orgdesc_10),
            getString(R.string.orgdesc_11),
            getString(R.string.orgdesc_12),
            getString(R.string.orgdesc_13),
            getString(R.string.orgdesc_14),
            getString(R.string.orgdesc_15),
            getString(R.string.orgdesc_16),
            getString(R.string.orgdesc_17)
        )
        orgLists = arrayOf(
            getString(R.string.org_1),
            getString(R.string.org_2),
            getString(R.string.org_3),
            getString(R.string.org_4),
            getString(R.string.org_5),
            getString(R.string.org_6),
            getString(R.string.org_7),
            getString(R.string.org_8),
            getString(R.string.org_9),
            getString(R.string.org_10),
            getString(R.string.org_11),
            getString(R.string.org_12),
            getString(R.string.org_13),
            getString(R.string.org_14),
            getString(R.string.org_15),
            getString(R.string.org_16),
            getString(R.string.org_17)
        )

        newRecyclerView = findViewById(R.id.recyclerview)
        newRecyclerView.layoutManager = LinearLayoutManager(this)
        newRecyclerView.setHasFixedSize(true)

        newArrayList = arrayListOf<Orgs>()
        getUserdata()

    }

    private fun getUserdata() {
        for (i in orgImages.indices) {
            val orgs = Orgs(orgImages[i], orgGroups[i], orgDescs[i], orgLists[i])
            newArrayList.add(orgs)


        }

        var adapter = OrgListAdapter(newArrayList)
        newRecyclerView.adapter = adapter

    }
}

