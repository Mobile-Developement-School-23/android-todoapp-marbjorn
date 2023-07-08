package com.example.todoapp.data

enum class State(var code : Int? = null) {
    Success(200),
    AbleToSync(0),
    BadRequest(400),
    AuthorizationProblem(401),
    NotFound(404),
    ServerError(500),
    UnknownServerError(502),
    OtherProblem(null);

    companion object {
        fun stateGenerator(code : Int?) : State = when(code) {
            1 -> Success
            2 -> AbleToSync
            400 -> BadRequest
            401 -> AuthorizationProblem
            404 -> NotFound
            500 -> ServerError
            502 -> UnknownServerError
            null -> OtherProblem
            else -> OtherProblem
        }
    }
}





