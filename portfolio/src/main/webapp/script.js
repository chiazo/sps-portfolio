// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

let last_hidden = "";

// handle change of html page
function navigate(page) {
    let url = window.location.href, next_page;

    if (page) {
        url = url.split("#")[0];
        url = url.split("?")[0];
        next_page = url + page;
    } else {
        next_page = url.split("comment.html")[0]
    }
    window.location.href = next_page;
}

// hide/show bio or photos on link click
function showElement(el_name) {
    const bio = document.getElementById("bio")
    const pics = document.getElementById("photography")
    const pic_title = document.getElementById("gallery_title")

    if (el_name === "bio") {
        if (bio.className.indexOf("hide") !== -1) {
            bio.classList.remove("hide")
        } else {
            const bio_b = document.getElementById("bio-b")
            bio.classList.add("hide")
            bio_b.setAttribute("href", "#")
        }
    } else {
        const pic_b = document.getElementById("pic-b")
        if (pics.className.indexOf("hide") !== -1) {
            pics.classList.remove("hide")
            pic_title.classList.remove("hide")
            pic_b.setAttribute("href", "#pics")
        } else {
            pics.classList.add("hide")
            pic_title.classList.add("hide")
            pic_b.setAttribute("href", "#")
        }
    }
}


// shows certain projects based on selected tag + button
function filterProjects(tag) {
    const allProjs = document.getElementsByClassName("proj");

    switch (tag) {
        case "all":
            for (let el of allProjs) {
                if (el.classList.contains("hide")) {
                    el.classList.remove("hide")

                }
            }
            break;

        default:
            for (let el of allProjs) {
                if (el.className.indexOf(tag) !== -1) {
                    if (el.classList.contains("hide")) {
                        el.classList.remove("hide")
                    }
                } else {
                    el.classList.add("hide")
                }
            }
            break;


    }
}

// show the return to top link after leaving top of page
window.addEventListener("scroll", e => {
    let returnLink = document.getElementById("return");
    let fromTop = window.scrollY;

    if (returnLink) {
        if (fromTop > 300) {
            returnLink.classList.remove("top")
        } else {
            returnLink.classList.add("top")
        }
    }

});

let last_clicked;

// add active class to selected button
document.addEventListener("DOMContentLoaded", function(e) {

    getComments();
    
    // set bio & photos to initially hidden
    const bio = document.getElementById("bio")
    const pics = document.getElementById("photography")

    const pic_title = document.getElementById("gallery_title")
    if (bio) {
        bio.classList.add("hide")
    }
    if (pics) {
        pics.classList.add("hide")
    }
    if (pic_title) {
        pic_title.classList.add("hide")
    }
    
    const buttons = document.getElementsByClassName("p-button");
    for (let b of buttons) {
        b.addEventListener("click", function() {
            if (b.className.indexOf("active") === -1) {
                b.classList.add("active")
            } else {
                b.classList.remove("active")
            }
            if (last_clicked) {
                last_clicked.classList.remove("active");
            }
            last_clicked = b;
        })
    }
})

// fetch login link 
async function login() {
    const response = await fetch('/login');
    const text = await response.text();
    window.location.href = text;
}

// fetch logout link 
async function logout() {
    const response = await fetch('/login');
    const text = await response.text();
    window.location.href = text;
}

// fetch content + append it to #fetched-content div
async function getComments() {
    let comment_div = document.createElement("div");

    const response = await fetch('/data');
    const text = await response.text();

    var all_comments = text.split("\n");
    all_comments = all_comments.filter(x => x.length > 0);

    all_comments.forEach(function(val, idx, arr) {
        arr[idx] = JSON.parse(val)
    })


    for (let submission of all_comments) {
        let curr_comment = document.createElement("div");
        curr_comment.classList += "comment";

        let name = document.createElement("span");
        name.innerText = submission.name;
        name.classList += " comment-name";

        let comment = document.createElement("span");
        comment.innerText = submission.comment;
        comment.classList += " comment-comment";

        let time = document.createElement("div");
        time.appendChild(document.createTextNode("submitted on: "));
        time.appendChild(document.createTextNode(submission.time));
        time.classList += " comment-time";

        curr_comment.appendChild(name);
        curr_comment.appendChild(document.createTextNode(": "));
        curr_comment.appendChild(comment);
        curr_comment.appendChild(time);
        comment_div.appendChild(curr_comment);

    }

    if (document.getElementById('fetched-content')) {
        document.getElementById('fetched-content').appendChild(comment_div);
    }

}
