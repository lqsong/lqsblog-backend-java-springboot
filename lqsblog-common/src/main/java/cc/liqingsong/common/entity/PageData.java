package cc.liqingsong.common.entity;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * list Data
 * @author liqingsong
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageData<T> {

    private Long total;
    private Long currentPage;
    private List<T> list;

    public PageData(IPage page) {
        this.total = page.getTotal();
        this.currentPage = page.getCurrent();
        this.list = page.getRecords();
    }


}
