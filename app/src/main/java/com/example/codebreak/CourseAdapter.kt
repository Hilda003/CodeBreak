package com.example.codebreak


import android.view.ViewGroup
import android.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.codebreak.databinding.ItemCourseBinding


class CourseAdapter: RecyclerView.Adapter<CourseAdapter.CourseViewHolder>() {




    class CourseViewHolder(private val binding: ItemCourseBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(course: Course) {
            binding.courseTitle.text = course.courseName
            binding.courseTime.text = course.courseDuration

            if(course.courseImage.isNotEmpty()){
                binding.courseImage.setImageResource(course.courseImage.toInt())
            }
        }

    }

    class CourseDiffCallback: DiffUtil.ItemCallback<Course>() {
        override fun areItemsTheSame(oldItem: Course, newItem: Course): Boolean {
            return oldItem.courseId == newItem.courseId
        }

        override fun areContentsTheSame(oldItem: Course, newItem: Course): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}