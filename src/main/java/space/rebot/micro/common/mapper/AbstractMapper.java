package space.rebot.micro.common.mapper;

public interface AbstractMapper <T, V>{
    T fromDto(V dto);
    V fromEntity(T entity);
}
