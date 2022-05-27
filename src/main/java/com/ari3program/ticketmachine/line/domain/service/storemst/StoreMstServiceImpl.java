package com.ari3program.ticketmachine.line.domain.service.storemst;

import java.util.List;
import org.springframework.stereotype.Service;

import com.ari3program.ticketmachine.line.domain.model.StoreMst;
import com.ari3program.ticketmachine.line.domain.repository.StoreMstRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class StoreMstServiceImpl implements StoreMstService {
	
	private StoreMstRepository storeMstRepository;
	
	public StoreMstServiceImpl(StoreMstRepository storeMstRepository) {
		this.storeMstRepository = storeMstRepository;
	}
	
	@Override
	public StoreMst findMyStoreMst(String channel_id) {
		List <StoreMst> result = storeMstRepository.findByChannelId(channel_id);
		if(result.size() == 1) {
			return result.get(0);
		}else {
			log.warn("対象のSTORE_MSTが見つかりません。 channel_id:{}", channel_id);
			throw new StoreMstNotFoundException("対象のSTORE_MSTが見つかりません。　channel_id:"+ channel_id);
		}
	}

}
