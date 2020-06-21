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
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/data")
public class DataServlet extends HttpServlet {

    private List<Comment> comments;
    private List<Integer> ids;

    public DataServlet() {
        comments = new ArrayList<Comment>();
        ids = new ArrayList<Integer>();
    }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    Query q = new Query("Comment").addSort("time", SortDirection.DESCENDING);
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    PreparedQuery pq = datastore.prepare(q);

        for (Entity submission : pq.asIterable()) {
            addComment((String) submission.getProperty("name"), (String) submission.getProperty("comment"), (Date) submission.getProperty("time"), (String) submission.getProperty("email"));
        }

      for (Comment submission : comments) {
          if (!ids.contains(submission.getId())) {
            ids.add(submission.getId());
            String json = commentToJson(submission);
            response.setContentType("application/json;");
            response.getWriter().println(json);
          } 
      }
    
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String name = request.getParameter("name");
    String comment = request.getParameter("comment");
    Date curr_time = new Date();

    Entity commentEntity = new Entity("Comment");
    UserService us = UserServiceFactory.getUserService();
    String email = us.getCurrentUser().getEmail();
    commentEntity.setProperty("email", email);
    commentEntity.setProperty("name", name);
    commentEntity.setProperty("comment", comment);
    commentEntity.setProperty("time", curr_time);
    commentEntity.setProperty("id", comments.size());

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    datastore.put(commentEntity);

    response.sendRedirect("/comment.html");
  }

    private String commentToJson(Comment comment) {
        Gson gson = new Gson();
        return gson.toJson(comment);
  
    }

    private void addComment(String name, String comment, Date time, String email) {
        Comment c = new Comment(name, comment, time, email, comments.size());
        comments.add(c);
    }

    private void deleteComment(int id) {
        Key commentEntityKey = KeyFactory.createKey("Comment", id);
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        datastore.delete(commentEntityKey);
    }
 

  }

  
