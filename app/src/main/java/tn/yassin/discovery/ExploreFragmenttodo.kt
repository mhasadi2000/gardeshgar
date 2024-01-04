package tn.yassin.discovery

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ScrollView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.facebook.shimmer.ShimmerFrameLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import tn.yassin.discovery.Models.PostsAdmin
import tn.yassin.discovery.Network.AdminApi
import tn.yassin.discovery.Network.retrofit
import tn.yassin.discovery.Views.Fragement.FragmentExplore
import tn.yassin.discovery.Views.Fragement.FragmentVR
import tn.yassin.oneblood.DataMapList.ExploreCategoryAdapter


class ExploreFragmenttodo : Fragment() {

    private lateinit var ExploreImgPreview: ImageView
    //
    private lateinit var RecyclerDesert: RecyclerView
    private lateinit var RecyclerBeach: RecyclerView
    private lateinit var RecyclerNature: RecyclerView
    private lateinit var RecyclerCulture: RecyclerView
    private lateinit var RecyclerSport: RecyclerView
    private lateinit var RecyclerArt: RecyclerView
    private lateinit var RecyclerFood: RecyclerView
    //
    lateinit var AdapterRecommendedDesert: ExploreCategoryAdapter
    lateinit var AdapterRecommendedPlage: ExploreCategoryAdapter
    lateinit var AdapterRecommendedNature: ExploreCategoryAdapter
    lateinit var AdapterRecommendedCulture: ExploreCategoryAdapter
    lateinit var AdapterRecommendedSport: ExploreCategoryAdapter
    lateinit var AdapterRecommendedArt: ExploreCategoryAdapter
    lateinit var AdapterRecommendedFood: ExploreCategoryAdapter
    //
    var PostsModels: ArrayList<PostsAdmin> = ArrayList()
    //
//    lateinit var shimmer_exploreDesert : ShimmerFrameLayout
//    lateinit var shimmer_explorePlage : ShimmerFrameLayout
//    lateinit var shimmer_exploreNature : ShimmerFrameLayout
//    lateinit var shimmer_exploreCulture : ShimmerFrameLayout
//    lateinit var shimmer_exploreSport : ShimmerFrameLayout
//    lateinit var shimmer_exploreArt : ShimmerFrameLayout
//    lateinit var shimmer_exploreFood : ShimmerFrameLayout
    //
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.exploretodo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //
        ExploreImgPreview = view.findViewById(R.id.ExploreImgPreview)

        //
        val CadreFood: View = view.findViewById(R.id.CadreFood)
        val LineFood: View = view.findViewById(R.id.LineFood)
        //ScrolltoSpecificView(CadreFood,LineFood)
        //
        RecyclerDesert = view.findViewById(R.id.RecyclerDesert)
        RecyclerBeach = view.findViewById(R.id.RecyclerBeach)
        RecyclerNature = view.findViewById(R.id.RecyclerNature)
        RecyclerCulture = view.findViewById(R.id.RecyclerCulture)
        RecyclerSport = view.findViewById(R.id.RecyclerSport)
        RecyclerArt= view.findViewById(R.id.RecyclerArt)
        RecyclerFood = view.findViewById(R.id.RecyclerFood)
        //
        //RecyclerDesert.setLayoutManager(StaggeredGridLayoutManager(1, 1))
        RecyclerDesert.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        AdapterRecommendedDesert = ExploreCategoryAdapter(requireContext())
        RecyclerDesert.adapter = AdapterRecommendedDesert
        ShowPostsDesert("Desert")
        //
        RecyclerBeach.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        AdapterRecommendedPlage = ExploreCategoryAdapter(requireContext())
        RecyclerBeach.adapter = AdapterRecommendedPlage
        ShowPostsPlage("Plage")
        //
        RecyclerNature.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        AdapterRecommendedNature = ExploreCategoryAdapter(requireContext())
        RecyclerNature.adapter = AdapterRecommendedNature
        ShowPostsNature("Nature")
        // aaa
        RecyclerCulture.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        AdapterRecommendedCulture = ExploreCategoryAdapter(requireContext())
        RecyclerCulture.adapter = AdapterRecommendedCulture
        ShowPostsCulture("Culture")
        //
        RecyclerSport.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        AdapterRecommendedSport = ExploreCategoryAdapter(requireContext())
        RecyclerSport.adapter = AdapterRecommendedSport
        ShowPostsSport("Activite")
        //
        RecyclerArt.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        AdapterRecommendedArt = ExploreCategoryAdapter(requireContext())
        RecyclerArt.adapter = AdapterRecommendedArt
        ShowPostsArt("Arts")
        //
        RecyclerFood.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        AdapterRecommendedFood = ExploreCategoryAdapter(requireContext())
        RecyclerFood.adapter = AdapterRecommendedFood
        ShowPostsFood("Food")
        //
//        shimmer_exploreDesert = view.findViewById(R.id.shimmer_exploreDesert)
//        shimmer_explorePlage = view.findViewById(R.id.shimmer_explorePlage)
//        shimmer_exploreNature = view.findViewById(R.id.shimmer_exploreNature)
//        shimmer_exploreCulture = view.findViewById(R.id.shimmer_exploreCulture)
//        shimmer_exploreSport = view.findViewById(R.id.shimmer_exploreSport)
//        shimmer_exploreArt = view.findViewById(R.id.shimmer_exploreArt)
//        shimmer_exploreFood = view.findViewById(R.id.shimmer_exploreFood)


    }

    fun ShowPostsDesert(TypeCategory: String) {
        val map: HashMap<String, String> = HashMap()
        map["categorie"] = TypeCategory
        PostsModels = PostData.fetchC1PostsModels()

        PostsModels.forEach{
            run {
                println(it)
            }
        }
        AdapterRecommendedDesert.setDataList(PostsModels)
        AdapterRecommendedDesert.notifyDataSetChanged()

    }

    fun ShowPostsNature(TypeCategory: String) {
        val map: HashMap<String, String> = HashMap()
        map["categorie"] = TypeCategory
        PostsModels = PostData.fetchC2PostsModels()
        AdapterRecommendedNature.setDataList(PostsModels)
        AdapterRecommendedNature.notifyDataSetChanged()
    }

        fun ShowPostsCulture(TypeCategory: String) {
            val map: HashMap<String, String> = HashMap()
            map["categorie"] = TypeCategory
            PostsModels = PostData.fetchC3PostsModels()
            AdapterRecommendedCulture.setDataList(PostsModels)
            AdapterRecommendedCulture.notifyDataSetChanged()
    }

    fun ShowPostsSport(TypeCategory: String) {
        val map: HashMap<String, String> = HashMap()
        map["categorie"] = TypeCategory
        PostsModels = PostData.fetchC4PostsModels()
        AdapterRecommendedSport.setDataList(PostsModels)
        AdapterRecommendedSport.notifyDataSetChanged()
    }

    fun ShowPostsArt(TypeCategory: String) {
        val map: HashMap<String, String> = HashMap()
        map["categorie"] = TypeCategory
        PostsModels = PostData.fetchC5PostsModels()
        AdapterRecommendedArt.setDataList(PostsModels)
        AdapterRecommendedArt.notifyDataSetChanged()
    }

    fun ShowPostsPlage(TypeCategory: String) {
        val map: HashMap<String, String> = HashMap()
        map["categorie"] = TypeCategory
        PostsModels = PostData.fetchC6PostsModels()
        AdapterRecommendedPlage.setDataList(PostsModels)
        AdapterRecommendedPlage.notifyDataSetChanged()
    }

    fun ShowPostsFood(TypeCategory: String) {
        val map: HashMap<String, String> = HashMap()
        map["categorie"] = TypeCategory
        PostsModels = PostData.fetchC7PostsModels()
        AdapterRecommendedFood.setDataList(PostsModels)
        AdapterRecommendedFood.notifyDataSetChanged()
    }


/*
    fun ScrolltoSpecificView(view: View , Line : View) {
        val targetView: View = view
        targetView.parent.requestChildFocus(targetView, targetView)
        Line.setBackground(resources.getDrawable(R.drawable.backgroundred))
    }
*/

    fun randomNumber(min: Int, max: Int): Int {
        return (min..max).random()
    }

    fun imageSlider(image: Int) {
        ExploreImgPreview.setImageResource(image)
    }

    fun autoSlider() {
        val imageList = arrayListOf(
            R.drawable.preview_sahara,
            R.drawable.img_chenini,
            R.drawable.preview_sidibou,
            R.drawable.preview_jamea,
            R.drawable.preview_plage,
            R.drawable.preview_traditional
        )
        var i = 0
        val imageSlider = object : Thread() {
            override fun run() {
                while (!isInterrupted) {
                    try {
                        sleep(5000)
                        activity?.runOnUiThread {
                            //imageSlider(imageList[randomNumber(0, imageList.size - 1)]) // random image
                            imageSlider(imageList[i])
                            if (i == imageList.size - 1) {
                                i = 0
                            } else {
                                i++
                            }
                        }
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                }
            }
        }
        imageSlider.start()
    }


    fun scrollToViewBottom(scrollView: ScrollView, childView: View) {
        val delay: Long = 500 //delay to let finish with possible modifications to ScrollView
        scrollView.postDelayed({ scrollView.smoothScrollTo(0, childView.bottom) }, delay)
    }

//    override fun onResume() {
//        super.onResume()
////        shimmer_exploreDesert.startShimmerAnimation()
////        shimmer_explorePlage.startShimmerAnimation()
//        shimmer_exploreNature.startShimmerAnimation()
//        shimmer_exploreCulture.startShimmerAnimation()
//        shimmer_exploreSport.startShimmerAnimation()
//        shimmer_exploreArt.startShimmerAnimation()
//        shimmer_exploreFood.startShimmerAnimation()
//    }

//    override fun onPause() {
////        shimmer_exploreDesert.stopShimmerAnimation()
////        shimmer_explorePlage.stopShimmerAnimation()
//        shimmer_exploreNature.stopShimmerAnimation()
//        shimmer_exploreCulture.stopShimmerAnimation()
//        shimmer_exploreSport.stopShimmerAnimation()
//        shimmer_exploreArt.stopShimmerAnimation()
//        shimmer_exploreFood.stopShimmerAnimation()
//        super.onPause()
//    }
}

