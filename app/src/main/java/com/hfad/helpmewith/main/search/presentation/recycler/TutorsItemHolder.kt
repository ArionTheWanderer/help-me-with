package com.hfad.helpmewith.main.search.presentation.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hfad.helpmewith.R
import com.hfad.helpmewith.app.data.model.TutorsSubjectModel
import com.hfad.helpmewith.app.data.model.UserWrapperModel
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_tutor.*

class TutorsItemHolder (
    override val containerView: View,
    private val subjectName: String,
    private val clickLambda: (UserWrapperModel) -> Unit
) : RecyclerView.ViewHolder(containerView),
    LayoutContainer {

    fun bind(tutor: UserWrapperModel) {
        val tutorsSubjects = tutor.tutorInfo.tutorsSubjects
        var subject: TutorsSubjectModel? = null
        if (tutorsSubjects != null && tutorsSubjects.isNotEmpty()) {
            for (tutorsSubject in tutorsSubjects) {
                if (tutorsSubject.subject == subjectName) {
                    subject = tutorsSubject
                }
            }
        }
        if (tutor.tutorInfo.isNotRemote == true) {
            iv_tutors_communication.setImageResource(R.drawable.ic_intramural)
        } else {
            iv_tutors_communication.setImageResource(R.drawable.ic_remote)
        }
        val flName = tutor.userInfo.firstName + " " + tutor.userInfo.lastName
        tv_tutors_tutor_name.text = flName
        tv_tutors_tutor_subject.text = subjectName
        tv_tutors_tutor_payment.text = subject?.hourlyFee.toString() ?: ""
        tv_tutors_tutor_city.text = tutor.tutorInfo.city
        btn_tutors_send.setOnClickListener {
            clickLambda(tutor)
        }
    }

    companion object {
        fun create(parent: ViewGroup, subjectName: String, clickLambda: (UserWrapperModel) -> Unit, ) =
            TutorsItemHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_tutor, parent, false),
                subjectName, clickLambda
            )
    }
}
