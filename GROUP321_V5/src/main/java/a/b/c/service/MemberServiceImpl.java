package a.b.c.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import a.b.c.dao.MemberDaoInterface;

@Component
public class MemberServiceImpl implements MemberServiceInterface {

	@Autowired
	MemberDaoInterface memberDao;

	@Override
	public int loginChk(Map map) {
		// TODO Auto-generated method stub
		return memberDao.loginChk(map);
	}

	@Override
	public List searchBoard(Map map) {
		// TODO Auto-generated method stub
		return memberDao.searchBoard(map);
	}

	@Override
	public List searchList(Map map) {
		// TODO Auto-generated method stub
		return memberDao.searchList(map);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int msgInsert(Map map) throws Exception {
		int result = 0;
		try {
			List list_maxCh = memberDao.maxCh_num(map);
			if (0 != list_maxCh.size()) {
				Map map_maxCH = new HashMap<>();
				for (int i = 0; i < list_maxCh.size(); i++) {
					map_maxCH = (Map) list_maxCh.get(i);
				}

				int ch_num = ((int) map_maxCH.get("MAX(ch_num)")) + 1;

				map.put("ch_num", ch_num);
				result = memberDao.msgInsert(map);
			}
		} catch (Exception e) {
			map.put("ch_num", 1);
			result = memberDao.msgInsert(map);
		}

		return result;
	}

	@Override
	public List msgSelect(Map map) throws Exception {

		return memberDao.msgSelect(map);
	}
}
