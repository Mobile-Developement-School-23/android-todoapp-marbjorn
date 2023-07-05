package com.example.todoapp.repository

enum class State(var code : Int? = null) {
    Success(200),
    AbleToSync(0),
    Bad_Request(400),
    Authorization_Fault(401),
    Not_Found(404),
    Server_Error(500),
    Unknown_Server_Error(502),
    OtherProblem(null)
}

fun stateGenerator(code : Int?) : State = when(code) {
    1 -> State.Success
    2 -> State.AbleToSync
    400 -> State.Bad_Request
    401 -> State.Authorization_Fault
    404 -> State.Not_Found
    500 -> State.Server_Error
    502 -> State.Unknown_Server_Error
    null -> State.OtherProblem
    else -> State.OtherProblem
}



