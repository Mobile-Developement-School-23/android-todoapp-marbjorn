package com.example.todoapp.repository

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
            1 -> State.Success
            2 -> State.AbleToSync
            400 -> State.BadRequest
            401 -> State.AuthorizationProblem
            404 -> State.NotFound
            500 -> State.ServerError
            502 -> State.UnknownServerError
            null -> State.OtherProblem
            else -> State.OtherProblem
        }
    }
}





