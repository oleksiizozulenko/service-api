/*
 * Copyright 2016 EPAM Systems
 * 
 * 
 * This file is part of EPAM Report Portal.
 * https://github.com/reportportal/service-api
 * 
 * Report Portal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Report Portal is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Report Portal.  If not, see <http://www.gnu.org/licenses/>.
 */ 

package com.epam.ta.reportportal.core.widget.content;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.epam.ta.reportportal.database.ActivityDocumentHandler;
import com.epam.ta.reportportal.database.dao.ActivityRepository;
import com.epam.ta.reportportal.database.search.Filter;
import com.epam.ta.reportportal.ws.model.widget.ChartObject;

/**
 * @author Dzmitry_Kavalets
 */
@Service("ActivityContentLoader")
public class ActivityContentLoader implements IContentLoadingStrategy {

	private static final String COLLECTION_NAME = "activity";

	@Autowired
	private ActivityRepository activityRepository;

	@Override
	public Map<String, List<ChartObject>> loadContent(Filter filter, Sort sorting, int quantity, List<String> contentFields,
			List<String> metaDataFields, Map<String, List<String>> options) {

		ActivityDocumentHandler activityDocumentHandler = new ActivityDocumentHandler();

		ArrayList<String> fields = new ArrayList<>();
		fields.addAll(contentFields);
		fields.addAll(metaDataFields);

		activityRepository.loadWithCallback(filter, sorting, quantity, fields, activityDocumentHandler, COLLECTION_NAME);
		Map<String, List<ChartObject>> result = new HashMap<>();
		result.put("result", activityDocumentHandler.getResult());
		return result;
	}
}