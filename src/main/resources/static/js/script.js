$(".answer-write button[type=submit]").click(addAnswer);
$(".delete-answer-form").click(deleteAnswer);

function addAnswer(e) {
    console.log("click me");
    e.preventDefault();

    var queryString = $(".answer-write").serialize();
    console.log("query : ", queryString);

    var url = $(".answer-write").attr("action");
    console.log("url : " + url);

    $.ajax({
        type: 'post',
        url: url,
        data: queryString,
        dataType: 'json',
        error: onError,
        success: onSuccess
    });
}

function onError() {
    console.log('실패')

}

function onSuccess(data, status) {
    console.log(data);
    var answerTemplate = $("#answerTemplate").html();
    var template = answerTemplate.format(data.writer.name, data.formattedLocalDateTime, data.contents, data.question.id, data.id);
    // var template = answerTemplate.format(data.writer.name,data.formattedLocalDateTime,data.content,data.id,data.id );
    $(".qna-comment-slipp-articles").prepend(template);
    $(".answer-write textarea").val("");
}

function deleteAnswer(e) {

    var deleteBtn = $(this);
    e.preventDefault();

    var url = deleteBtn.attr("action");
    console.log("url: " + url);

    $.ajax({
        type: 'delete',
        url: url,
        dataType: 'json',
        error: function (xhr, status) {
            console.log("답변을 지우는데에 실패하였습니다.");
        },
        success: function (data, status) {
            if (data.valid) {
                deleteBtn.closest("article").remove();
            }
            else{
                alert(data.errorMessage);
            }
        }
    });
}


String.prototype.format = function () {
    var args = arguments;
    return this.replace(/{(\d+)}/g, function (match, number) {
        return typeof args[number] != 'undefined'
            ? args[number]
            : match
            ;
    });
}
