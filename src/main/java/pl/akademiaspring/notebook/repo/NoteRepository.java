package pl.akademiaspring.notebook.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.akademiaspring.notebook.model.Note;

import java.util.List;

@Repository
public interface NoteRepository extends CrudRepository<Note, Long> {

    Note findById(long id);

    List<Note> findAll();
}
