package hdfg159.chattogether.data;

import hdfg159.chattogether.domain.Suggestion;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Project:ChatTogether
 * Package:hdfg159.chattogether.data
 * Created by hdfg159 on 18-3-25 下午4:38.
 */
public interface SuggestionRepository extends JpaRepository<Suggestion, Long> {
}
