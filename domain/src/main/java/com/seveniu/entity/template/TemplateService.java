package com.seveniu.entity.template;

import com.seveniu.entity.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Created by dhlz on 12/31/16.
 * *
 */
@Service
public interface TemplateService extends BaseService<Template, Long> {


    Page<Template> findByWebsite(Long websiteId, Pageable pageable);
}
