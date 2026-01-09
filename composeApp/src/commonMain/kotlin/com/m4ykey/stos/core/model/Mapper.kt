package com.m4ykey.stos.core.model

import com.m4ykey.core.model.safe
import com.m4ykey.stos.answer.data.network.dto.AnswerCommentDto
import com.m4ykey.stos.answer.domain.model.AnswerComment
import com.m4ykey.stos.question.data.network.dto.ClosedDetailsDto
import com.m4ykey.stos.question.data.network.dto.QuestionAnswerDto
import com.m4ykey.stos.question.data.network.dto.QuestionCommentDto
import com.m4ykey.stos.question.data.network.dto.QuestionDetailDto
import com.m4ykey.stos.question.data.network.dto.QuestionDto
import com.m4ykey.stos.question.domain.model.BadgeCounts
import com.m4ykey.stos.question.domain.model.ClosedDetails
import com.m4ykey.stos.question.domain.model.Question
import com.m4ykey.stos.question.domain.model.QuestionAnswer
import com.m4ykey.stos.question.domain.model.QuestionComment
import com.m4ykey.stos.question.domain.model.QuestionDetail
import com.m4ykey.stos.user.data.network.dto.BadgeCountsDto
import com.m4ykey.stos.user.data.network.dto.UserDto
import com.m4ykey.stos.user.domain.model.User

fun UserDto.toDomain() = User(
    userId = userId.safe,
    reputation = reputation.safe,
    profileImage = profileImage.orEmpty(),
    link = link.orEmpty(),
    displayName = displayName.orEmpty(),
    badgeCounts = badgeCounts?.toDomain() ?: BadgeCounts.EMPTY
)

fun QuestionCommentDto.toDomain() = QuestionComment(
    body = body.orEmpty(),
    bodyMarkdown = bodyMarkdown.orEmpty(),
    creationDate = creationDate.safe,
    owner = owner?.toDomain() ?: User.EMPTY,
    commentId = commentId.safe
)

fun ClosedDetailsDto.toDomain() = ClosedDetails(
    reason = reason.orEmpty(),
    description = description.orEmpty(),
    originalQuestion = originalQuestions.orEmpty()
)

fun QuestionAnswerDto.toDomain() = QuestionAnswer(
    answerId = answerId.safe,
    bodyMarkdown = bodyMarkdown.orEmpty(),
    creationDate = creationDate.safe,
    downVoteCount = downVoteCount.safe,
    upVoteCount = upVoteCount.safe,
    owner = owner?.toDomain() ?: User.EMPTY,
    isAccepted = isAccepted,
    commentCount = commentCount.safe
)

fun QuestionDetail.toQuestion() = Question(
    title = title,
    answerCount = answerCount,
    owner = owner,
    creationDate = creationDate,
    downVoteCount = downVoteCount,
    questionId = questionId,
    upVoteCount = upVoteCount,
    viewCount = viewCount
)

fun QuestionDetailDto.toDomain() = QuestionDetail(
    answerCount = answerCount.safe,
    bodyMarkdown = bodyMarkdown.orEmpty(),
    creationDate = creationDate.safe,
    downVoteCount = downVoteCount.safe,
    lastActivityDate = lastActivityDate.safe,
    link = link.orEmpty(),
    questionId = questionId.safe,
    title = title.orEmpty(),
    viewCount = viewCount.safe,
    tags = tags.orEmpty(),
    upVoteCount = upVoteCount.safe,
    owner = owner?.toDomain() ?: User.EMPTY,
    closedDetails = closedDetails?.toDomain() ?: ClosedDetails.EMPTY,
    commentCount = commentCount.safe,
    lastEditor = lastEditor?.toDomain() ?: User.EMPTY,
    lastEditDate = lastEditDate.safe,
    closedDate = closedDate.safe
)

fun BadgeCountsDto.toDomain() = BadgeCounts(
    bronze = bronze.safe,
    silver = silver.safe,
    gold = gold.safe
)

fun QuestionDto.toDomain() = Question(
    title = title.orEmpty(),
    viewCount = viewCount.safe,
    questionId = questionId.safe,
    upVoteCount = upVoteCount.safe,
    downVoteCount = downVoteCount.safe,
    answerCount = answerCount.safe,
    creationDate = creationDate.safe,
    owner = owner?.toDomain() ?: User.EMPTY
)

fun AnswerCommentDto.toDomain() = AnswerComment(
    body = body.orEmpty(),
    bodyMarkdown = bodyMarkdown.orEmpty(),
    commentId = commentId.safe,
    creationDate = creationDate.safe,
    owner = owner?.toDomain() ?: User.EMPTY
)