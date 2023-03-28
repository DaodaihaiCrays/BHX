package com.bhx.bhx.View.HomeFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bhx.bhx.Model.ReviewCategory
import com.bhx.bhx.Model.Product
import com.bhx.bhx.R
import java.time.ZonedDateTime

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private lateinit var revProducts: RecyclerView
    private lateinit var categories: MutableList<ReviewCategory>
    private lateinit var adapter: ProductAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view: View = inflater.inflate(R.layout.fragment_home, container, false)
        initDateCategories()
        revProducts = view.findViewById(R.id.revProduct)
        adapter = ProductAdapter(categories, container!!.context)
        revProducts.adapter= adapter
        revProducts.layoutManager = LinearLayoutManager(container!!.context, RecyclerView.VERTICAL, false)

        return view
    }

    private fun initDateCategories() {
        categories = mutableListOf()
        categories.add(ReviewCategory("1", "Thịt, cá, trứng1", 300, listOf(
            Product(1, "Thịt", 10.0, 100, 1,"Ngon",null,null, null, null, ZonedDateTime.parse("2023-03-28T01:27:15.078775Z"),ZonedDateTime.parse("2023-03-28T01:27:15.078775Z"), "https://chamchut.com/wp-content/uploads/2018/03/huong-dan-chon-mua-va-cach-bao-quan-thit-ca-trong-tu-lanh2.jpg"),
            Product(1, "Thịt", 10.0, 100, 1,"Ngon",null,null, null, null, ZonedDateTime.parse("2023-03-28T01:27:15.078775Z"),ZonedDateTime.parse("2023-03-28T01:27:15.078775Z"), "https://chamchut.com/wp-content/uploads/2018/03/huong-dan-chon-mua-va-cach-bao-quan-thit-ca-trong-tu-lanh2.jpg"),
            Product(1, "Thịt", 10.0, 100, 1,"Ngon",null,null, null, null, ZonedDateTime.parse("2023-03-28T01:27:15.078775Z"),ZonedDateTime.parse("2023-03-28T01:27:15.078775Z"), "https://chamchut.com/wp-content/uploads/2018/03/huong-dan-chon-mua-va-cach-bao-quan-thit-ca-trong-tu-lanh2.jpg"),
            Product(1, "Thịt", 10.0, 100, 1,"Ngon",null,null, null, null, ZonedDateTime.parse("2023-03-28T01:27:15.078775Z"),ZonedDateTime.parse("2023-03-28T01:27:15.078775Z"), "https://chamchut.com/wp-content/uploads/2018/03/huong-dan-chon-mua-va-cach-bao-quan-thit-ca-trong-tu-lanh2.jpg"),
            Product(1, "Thịt", 10.0, 100, 1,"Ngon",null,null, null, null, ZonedDateTime.parse("2023-03-28T01:27:15.078775Z"),ZonedDateTime.parse("2023-03-28T01:27:15.078775Z"), "https://chamchut.com/wp-content/uploads/2018/03/huong-dan-chon-mua-va-cach-bao-quan-thit-ca-trong-tu-lanh2.jpg"),
            Product(1, "Thịt", 10.0, 100, 1,"Ngon",null,null, null, null, ZonedDateTime.parse("2023-03-28T01:27:15.078775Z"),ZonedDateTime.parse("2023-03-28T01:27:15.078775Z"), "https://chamchut.com/wp-content/uploads/2018/03/huong-dan-chon-mua-va-cach-bao-quan-thit-ca-trong-tu-lanh2.jpg"),
        )))
        categories.add(ReviewCategory("1", "Thịt, cá, trứng1", 300, listOf(
            Product(1, "Thịt", 10.0, 100, 1,"Ngon",null,null, null, null, ZonedDateTime.parse("2023-03-28T01:27:15.078775Z"),ZonedDateTime.parse("2023-03-28T01:27:15.078775Z"), "https://chamchut.com/wp-content/uploads/2018/03/huong-dan-chon-mua-va-cach-bao-quan-thit-ca-trong-tu-lanh2.jpg"),
            Product(1, "Thịt", 10.0, 100, 1,"Ngon",null,null, null, null, ZonedDateTime.parse("2023-03-28T01:27:15.078775Z"),ZonedDateTime.parse("2023-03-28T01:27:15.078775Z"), "https://chamchut.com/wp-content/uploads/2018/03/huong-dan-chon-mua-va-cach-bao-quan-thit-ca-trong-tu-lanh2.jpg"),
            Product(1, "Thịt", 10.0, 100, 1,"Ngon",null,null, null, null, ZonedDateTime.parse("2023-03-28T01:27:15.078775Z"),ZonedDateTime.parse("2023-03-28T01:27:15.078775Z"), "https://chamchut.com/wp-content/uploads/2018/03/huong-dan-chon-mua-va-cach-bao-quan-thit-ca-trong-tu-lanh2.jpg"),
            Product(1, "Thịt", 10.0, 100, 1,"Ngon",null,null, null, null, ZonedDateTime.parse("2023-03-28T01:27:15.078775Z"),ZonedDateTime.parse("2023-03-28T01:27:15.078775Z"), "https://chamchut.com/wp-content/uploads/2018/03/huong-dan-chon-mua-va-cach-bao-quan-thit-ca-trong-tu-lanh2.jpg"),
            Product(1, "Thịt", 10.0, 100, 1,"Ngon",null,null, null, null, ZonedDateTime.parse("2023-03-28T01:27:15.078775Z"),ZonedDateTime.parse("2023-03-28T01:27:15.078775Z"), "https://chamchut.com/wp-content/uploads/2018/03/huong-dan-chon-mua-va-cach-bao-quan-thit-ca-trong-tu-lanh2.jpg"),
            Product(1, "Thịt", 10.0, 100, 1,"Ngon",null,null, null, null, ZonedDateTime.parse("2023-03-28T01:27:15.078775Z"),ZonedDateTime.parse("2023-03-28T01:27:15.078775Z"), "https://chamchut.com/wp-content/uploads/2018/03/huong-dan-chon-mua-va-cach-bao-quan-thit-ca-trong-tu-lanh2.jpg"),
        )))
        categories.add(ReviewCategory("1", "Thịt, cá, trứng1", 300, listOf(
            Product(1, "Thịt", 10.0, 100, 1,"Ngon",null,null, null, null, ZonedDateTime.parse("2023-03-28T01:27:15.078775Z"),ZonedDateTime.parse("2023-03-28T01:27:15.078775Z"), "https://chamchut.com/wp-content/uploads/2018/03/huong-dan-chon-mua-va-cach-bao-quan-thit-ca-trong-tu-lanh2.jpg"),
            Product(1, "Thịt", 10.0, 100, 1,"Ngon",null,null, null, null, ZonedDateTime.parse("2023-03-28T01:27:15.078775Z"),ZonedDateTime.parse("2023-03-28T01:27:15.078775Z"), "https://chamchut.com/wp-content/uploads/2018/03/huong-dan-chon-mua-va-cach-bao-quan-thit-ca-trong-tu-lanh2.jpg"),
            Product(1, "Thịt", 10.0, 100, 1,"Ngon",null,null, null, null, ZonedDateTime.parse("2023-03-28T01:27:15.078775Z"),ZonedDateTime.parse("2023-03-28T01:27:15.078775Z"), "https://chamchut.com/wp-content/uploads/2018/03/huong-dan-chon-mua-va-cach-bao-quan-thit-ca-trong-tu-lanh2.jpg"),
            Product(1, "Thịt", 10.0, 100, 1,"Ngon",null,null, null, null, ZonedDateTime.parse("2023-03-28T01:27:15.078775Z"),ZonedDateTime.parse("2023-03-28T01:27:15.078775Z"), "https://chamchut.com/wp-content/uploads/2018/03/huong-dan-chon-mua-va-cach-bao-quan-thit-ca-trong-tu-lanh2.jpg"),
            Product(1, "Thịt", 10.0, 100, 1,"Ngon",null,null, null, null, ZonedDateTime.parse("2023-03-28T01:27:15.078775Z"),ZonedDateTime.parse("2023-03-28T01:27:15.078775Z"), "https://chamchut.com/wp-content/uploads/2018/03/huong-dan-chon-mua-va-cach-bao-quan-thit-ca-trong-tu-lanh2.jpg"),
            Product(1, "Thịt", 10.0, 100, 1,"Ngon",null,null, null, null, ZonedDateTime.parse("2023-03-28T01:27:15.078775Z"),ZonedDateTime.parse("2023-03-28T01:27:15.078775Z"), "https://chamchut.com/wp-content/uploads/2018/03/huong-dan-chon-mua-va-cach-bao-quan-thit-ca-trong-tu-lanh2.jpg"),
        )))

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}