package tn.yassin.oneblood.DataMapList

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.preference.PreferenceManager
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.coroutines.*
import tn.yassin.discovery.DataMapList.RecommendedViewHolder
import tn.yassin.discovery.Models.PostsAdmin
import tn.yassin.discovery.R
import tn.yassin.discovery.Utils.ReadyFunction
import tn.yassin.discovery.ViewModel.FavorisViewModel
import tn.yassin.discovery.Views.CustomDialog.DialogFavoris
import java.io.ByteArrayOutputStream
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import android.graphics.drawable.Drawable



class RecommendedAdapter(var context: Context,val limit: Int) :
    RecyclerView.Adapter<RecommendedViewHolder>() {

    var dataList = mutableListOf<PostsAdmin>()
    var filteredPostsList= ArrayList<PostsAdmin>()
    val ReadyFunction = ReadyFunction()


    internal fun setDataList(PostsArrayList: ArrayList<PostsAdmin>) {
        this.dataList = PostsArrayList
        this.filteredPostsList = PostsArrayList
        notifyDataSetChanged()
    }
    internal fun addDataList(data: PostsAdmin) {
        this.dataList.add(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendedViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_recommended, parent, false)
        return RecommendedViewHolder(view)

    }


    //override fun getItemCount() = filteredPostsList.size


    ////////////////////////////////////

    override fun getItemCount(): Int {
        return if (filteredPostsList.size > limit) {
            limit
        } else {
            filteredPostsList.size
        }
    }
    ////////////////////////////////////

    fun encodeTobase64(image: Bitmap): String? {
        val baos = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val b = baos.toByteArray()
        val imageEncoded = Base64.encodeToString(b, Base64.DEFAULT)
        println("Image Log:"+ imageEncoded)
        return imageEncoded
    }

    override fun onBindViewHolder(holder: RecommendedViewHolder, @SuppressLint("RecyclerView") position: Int) {
        var data = filteredPostsList[position]

        val ImagePlace = ("https://location-android-pr.storage.iran.liara.space/" + filteredPostsList[position].photo)
        val imageUrl = "https://location-android-pr.storage.iran.liara.space/azadi.jpg"

        // Load the image from the URL into the ImageView using Picasso
        Picasso.get().load(imageUrl).into(object : Target {
            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                if (bitmap != null) {
                    // Encode the loaded bitmap to Base64
                    val base64String = encodeTobase64(bitmap)
                    println("base bitmap loadeddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd")
                    if (base64String != null) {
                        // Perform actions when the image is loaded successfully
                        val encodedImagess = base64String
                        println(encodedImagess)

                        // Set the image in the ViewHolder
                        Glide.with(context)
                            .load(ImagePlace)
                            .fitCenter()
                            .into(holder.PicRecomm)

                        // Other data
                        val NomPlace = filteredPostsList[position].nom
                        val Lieux = filteredPostsList[position].lieux
                        val RatingPlace = filteredPostsList[position].rate

                        holder.PlaceName.text = NomPlace
                        holder.LieuxPlace.text = Lieux
                        holder.ratingBar.rating = RatingPlace!!.toFloat()
                        holder.itemView.setBackgroundColor(Color.parseColor("#FAFAFA"));

                        //animation Items RecyclerView
                        val animation =
                            AnimationUtils.loadAnimation(holder.itemView.context, R.anim.item_animation_fall_down)
                        holder.itemView.startAnimation(animation)

                        // Set onClickListener
                        holder.itemView.setOnClickListener {
                            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
                            val editor = preferences.edit()
                            editor.putString("IdPost", data.id)
                            editor.putString("ImagePlace", encodedImagess)
                            editor.putString("NomPlace", data.nom)
                            editor.putString("LieuxPlace", data.lieux)
                            editor.putString("DescriptionPlace", data.description)
                            editor.putString("RatingPlace", data.rate)
                            editor.apply()  // Save Data

                            // PopUpDetails should be called here
                            PopUpDetails(holder.itemView)

                            // ScreenShot
                            val fileOutputStream = ByteArrayOutputStream()
                            ReadyFunction.ScreenShot(holder.itemView)!!.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)
                            val compressImage: ByteArray = fileOutputStream.toByteArray()
                            val sEncodedImage: String = Base64.encodeToString(compressImage, Base64.DEFAULT)
                            println("sEncodedImage ===>>>>>> " + sEncodedImage)
                            val preferencess = PreferenceManager.getDefaultSharedPreferences(context)
                            val editorr = preferencess.edit()
                            editor.putString("ImagePlace", sEncodedImage)
                            editorr.putString("ScreenShotAdmin", sEncodedImage)
                            editorr.apply()  // Save Data
                        }
                    }
                }
            }

            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                // Handle the case where bitmap loading failed
            }

            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
                // Optionally handle the case where the image is still loading
            }
        })
    }
    fun PopUpDetails(view: View) {
        PopUpDetailsPosts(view.context)
    }

    fun PopUpDetailsPosts(context: Context) {
        /*        val factoryyy = ViewModelProvider.NewInstanceFactory()
                val viewModel: FavorisViewModel = factoryyy.create(FavorisViewModel::class.java)*/
        val androidFactory =
            ViewModelProvider.AndroidViewModelFactory(context.applicationContext as Application)
        val viewModell: FavorisViewModel = androidFactory.create(FavorisViewModel::class.java)
        //////////////////
        val Jooobbb = GlobalScope.launch(Dispatchers.Default) {
            viewModell.VerifFavoriteCoroutineScope(context)
            // delay the coroutine by 1sec
            delay(1000)
        }
        //////////////////
        runBlocking {
            Jooobbb.join()
            println("Blooocck")
            //update the UI
            val factory = LayoutInflater.from(context)
            val view: View = factory.inflate(R.layout.detailsposts, null)
            val msg = DialogFavoris()
            msg.ShowDetailsPost(context, view)
        }
        //////////////////
    }





}


