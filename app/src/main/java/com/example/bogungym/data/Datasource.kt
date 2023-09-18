package com.example.bogungym.data

import com.example.bogungym.R
import com.example.bogungym.data.model.BodyPart
import com.example.bogungym.data.model.Supplements
import retrofit2.http.Body

class Datasource {

    fun loadBodyParts(): List<BodyPart> {
        return listOf(
            BodyPart("biceps", R.drawable.biceps),
            BodyPart("abs", R.drawable.abs),
            BodyPart("calves", R.drawable.calf),
            BodyPart("delts", R.drawable.deadlift),
            BodyPart("forearms", R.drawable.forearm),
            BodyPart("glutes", R.drawable.gluteus),
            BodyPart("hamstrings", R.drawable.hamstring),
            BodyPart("lats", R.drawable.lats),
            BodyPart("pectorals", R.drawable.chest),
            BodyPart("quads", R.drawable.legs),
            BodyPart("spine", R.drawable.spine),
            BodyPart("triceps", R.drawable.triceps),
            BodyPart("traps", R.drawable.traps)

        )
    }


    fun loadSupplements(): List<Supplements> {
        return listOf(
            Supplements("Beta Alanin", R.drawable.alnine, R.string.alanin,false),
            Supplements("Whey Protein", R.drawable.whey, R.string.protein,true),
            Supplements("Creatine-Monohydrate", R.drawable.creatine, R.string.creatine,true),
            Supplements("BCAA", R.drawable.bcaa, R.string.bcaa,true),
            Supplements("Casein", R.drawable.casein, R.string.casein,true),
            Supplements("Fat Burners", R.drawable.fatburner, R.string.fat_burner,false),
            Supplements("Glutamine", R.drawable.glutamin, R.string.glutamine,true),
            Supplements("Grow Hormone", R.drawable.grow_hormone, R.string.grow_hormone,false),
            Supplements("L-Arginine", R.drawable.larginin, R.string.l_arginine,false),
            Supplements("Mass Gainer", R.drawable.massgainer, R.string.gainer,true),
            Supplements("Omega 3-Fish Oil", R.drawable.omega3, R.string.omega_oil,true),
            Supplements("Pre-Workout", R.drawable.preworkout, R.string.pre_workout,false),
            Supplements("Zinc Vitamine", R.drawable.zink_vitamini, R.string.zink_vitamins,true),
            Supplements("Testosterone Pills",R.drawable.testosteron,R.string.testosteron,false)

        )
    }


}