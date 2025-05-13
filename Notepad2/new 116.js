document.addEventListener("DOMContentLoaded", function () {
    if (!localStorage.getItem("firstVisitDone")) {
        localStorage.setItem("firstVisitDone", "true");
        const button = document.getElementById("_oj19|text");
        if (button) {
            button.click();
        }
    }
})

action-_startOver action-button oj-conveyorbelt-item oj-button oj-button-full-chrome oj-button-text-only oj-complete oj-enabled oj-default
window.onload = function () {
 const button = document.getElementById("_oj19|text");
        if (button) {
            button.click();
        }
}


 document.addEventListener('DOMContentLoaded', function () {
            setTimeout(function () {
                document.getElementById('_oj19|text').click();
            }, 10); // Delay of 5 seconds
        });
