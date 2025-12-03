package com.m4ykey.stos.answer.data.mapper

import com.m4ykey.stos.answer.data.network.dto.AnswerCommentDto
import com.m4ykey.stos.answer.domain.model.AnswerComment
import com.m4ykey.stos.question.data.mapper.toDomain
import com.m4ykey.stos.question.domain.model.QuestionOwner

fun AnswerCommentDto.toDomain() = AnswerComment(
    body = body.orEmpty(),
    bodyMarkdown = bodyMarkdown.orEmpty(),
    commentId = commentId ?: 0,
    creationDate = creationDate ?: 0,
    owner = owner?.toDomain() ?: QuestionOwner.EMPTY
)