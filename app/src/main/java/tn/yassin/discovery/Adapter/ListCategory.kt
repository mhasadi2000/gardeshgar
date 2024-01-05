package tn.yassin.discovery.Adapter

import tn.yassin.discovery.R
import tn.yassin.discovery.data.Category

class ListCategory {

    var ListCategory: MutableList<Category> = ArrayList()


    fun initListCategory()
    {
//        ListCategory.add(
//            Category(
//                NomCategory = "مذهبی",
//                IconCategory = R.drawable.mosq
//            )
//        )
        ListCategory.add(
            Category(
                NomCategory = "برجسته",
                IconCategory = R.drawable.icon_sport
            )
        )
        ListCategory.add(
            Category(
                NomCategory = "طبیعت",
                IconCategory = R.drawable.icon_nature
            )
        )
        ListCategory.add(
            Category(
                NomCategory = "تاریخی",
                IconCategory = R.drawable.icon_history
            )
        )
        ListCategory.add(
            Category(
                NomCategory = "رستوران",
                IconCategory = R.drawable.icon_restau
            )
        )
        ListCategory.add(
            Category(
                NomCategory = "فروشگاه",
                IconCategory = R.drawable.icon_arts
            )
        )
        ListCategory.add(
            Category(
                NomCategory = "حومه",
                IconCategory = R.drawable.icon_sahara
            )
        )
    }
}