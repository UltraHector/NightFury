package com.TroyEmpire.NightFury.Ghost.IService;

import java.util.Date;
import java.util.List;

import com.TroyEmpire.NightFury.Entity.News;
import com.TroyEmpire.NightFury.Enum.NewsType;

public interface IInformationPlatformService {
	/**
	 * check and download more news from server
	 */
	public void updateNews(NewsType type);

	/**
	 * @param startDate
	 *            , the startDate from which a number of limit old news will be
	 *            fetched if startDate == null, the latest news in the storage
	 *            will be returned
	 * @param the
	 *            number of news will be returned
	 */
	public List<News> getNewsFromStorage(NewsType type, Date startDate, int limit);

	public News getNewsByid(long id);
}
