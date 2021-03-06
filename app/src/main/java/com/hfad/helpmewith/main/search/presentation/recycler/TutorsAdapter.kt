package com.hfad.helpmewith.main.search.presentation.recycler

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hfad.helpmewith.app.data.model.UserWrapperModel

class TutorsAdapter (
    private var dataSource: List<UserWrapperModel>,
    private val subjectName: String,
    private val sendOfferLambda: (UserWrapperModel) -> Unit,
    private val showTutorLambda: (UserWrapperModel) -> Unit
) : RecyclerView.Adapter<TutorsItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TutorsItemHolder =
        TutorsItemHolder.create(parent, subjectName, sendOfferLambda, showTutorLambda)

    override fun onBindViewHolder(holder: TutorsItemHolder, position: Int) =
        holder.bind(dataSource[position])

    override fun getItemCount(): Int = dataSource.size
}
