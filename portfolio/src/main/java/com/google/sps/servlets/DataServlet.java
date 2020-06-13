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

package com.google.sps.servlets;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import com.google.sps.data.Comment;
import com.google.gson.Gson;

/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/data")
public class DataServlet extends HttpServlet {

    List<Comment> comments = new ArrayList<Comment>();
    
    public DataServlet() {
        addComment("hi", "what", new Date());
        addComment("no", "yay", new Date());
    }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

      for (Comment submission : comments) {
          String json = commentToJson(submission);
          response.setContentType("application/json;");
          response.getWriter().println(json);
      }
    
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
      
    String name = request.getParameter("name");
    String comment = request.getParameter("comment");
    Date curr_time = new Date();
    addComment(name, comment, curr_time);

    response.setContentType("text/html;");
    

    for (Comment submission : comments) {
        response.getWriter().println(submission.getName() + " - " + submission.getComment() + " - " + submission.getTime().toString());
      }

    response.sendRedirect("/comment.html");
  }

    private String commentToJson(Comment comment) {
        Gson gson = new Gson();
        return gson.toJson(comment);
  
    }

    private void addComment(String name, String comment, Date time) {
        Comment c = new Comment(name, comment, time);
        comments.add(c);
    }
 

  }

  
