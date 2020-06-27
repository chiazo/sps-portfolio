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

package com.google.sps;

import java.util.Collection;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.Iterator;


public final class FindMeetingQuery {
  public Collection<TimeRange> query(Collection<Event> events, MeetingRequest request) {

    List<TimeRange> availableTimes = new ArrayList<>();
    long requestDuration = request.getDuration();

    // edge cases
    if (requestDuration > 1440) {
        return availableTimes;
    }

    TimeRange timeLeft = TimeRange.fromStartDuration(0, 24 * 60);

    for (Event e : events) {
        TimeRange currEvent = e.getWhen();

        if (timeLeft.overlaps(currEvent) && checkDuplicateAttendees(request.getAttendees(), e.getAttendees())) {
            TimeRange newSlot = TimeRange.fromStartEnd(timeLeft.start(), currEvent.start(), false);
            timeLeft = TimeRange.fromStartEnd(currEvent.end(), 1440, false);
            if (newSlot.duration() > 0 && newSlot.duration() >= request.getDuration()) {
                availableTimes.add(newSlot);
            }
        } 
   
    }

    if (timeLeft.duration() > 0 && timeLeft.duration() >= request.getDuration()) {
        availableTimes.add(timeLeft);
    }

    return availableTimes;
  }

  boolean checkDuplicateAttendees(Collection<String> s1, Set<String> s2) {
      Iterator<String> iterator = s1.iterator();
 
        while (iterator.hasNext()) {
            String currPerson = iterator.next();

            if (s2.contains(currPerson)){

                return true;
            }
        }

        return false;
  }
}

