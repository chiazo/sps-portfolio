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

package com.google.sps.data;
import java.util.Date;

public final class Comment {

  private final String name;
  private final String comment;
  private final String email;
  private final Date time;
  private final int id;

  public Comment(String name, String comment, Date time, int id) {
      this.name = name;
      this.comment = comment;
      this.time = time;
      this.email = null;
      this.id = id;
  }

  public Comment(String name, String comment, Date time, String email, int id) {
        this.name = name;
        this.comment = comment;
        this.time = time;
        this.email = email;
        this.id = id;
  }

  public Date getTime() {
    return time;
  }

  public String getName() {
    return name;
  }

  public String getComment() {
    return comment;
  }

  public String getEmail() {
    return email;
  }

  public int getId() {
      return id;
  }
}
