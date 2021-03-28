package app.isparks.dao.repository.impl;

import app.isparks.core.pojo.entity.FFile;
import app.isparks.dao.mybatis.mapper.FileMapper;
import app.isparks.dao.repository.AbstractFileCurd;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author chenghd
 * @date 2020/9/29
 */
@Repository
public class FileCurdImpl extends AbstractFileCurd {

    private FileMapper fileMapper;

    public FileCurdImpl(FileMapper fileMapper){
        this.fileMapper = fileMapper;
    }

    @Override
    public FFile insert(FFile f) {
        beforeInsert(f);
        return fileMapper.insert(f) == 1 ? f : null;
    }

    @Override
    public FFile updateById(FFile f) {
        beforeUpdate(f);
        return fileMapper.updateById(f) == 1 ? f : null ;
    }

    @Override
    public List<FFile> selectByCond(FFile f) {
        return fileMapper.selectByCond(f);
    }

    @Override
    public long countBy(FFile fFile) {
        return fileMapper.countByCond(fFile);
    }

    @Override
    protected int deleteBy(FFile fFile) {
        return fileMapper.deleteByCond(fFile);
    }
}
