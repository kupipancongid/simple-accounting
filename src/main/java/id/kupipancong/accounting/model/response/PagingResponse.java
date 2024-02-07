package id.kupipancong.accounting.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PagingResponse {
    @JsonProperty("total_records")
    private Long totalRecords;
    @JsonProperty("current_page")
    private Integer currentPage;
    @JsonProperty("total_page")
    private Integer totalPage;
    private Integer size;
    @JsonProperty("next_page")
    private Integer nextPage;
    @JsonProperty("prev_page")
    private Integer previousePage;
}