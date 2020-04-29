package estudiante.dao;

import java.awt.List;
import java.util.Comparator;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import estudiante.model.Estudiante;
import estudiante.service.ListaEstudiante;

public class EstudianteDAO {
	
	List<Estudiante> listaEstudiantes = ListaEstudiante.getListaEstudiante();
	
	public List<Estudiante> obtenerEstudiantes(){
		return listaEstudiantes;	
	}
	
	public List<Estudiante> buscarPorNombre(String nombres){		
		Comparator<Estudiante> groupByComparator = Comparator.comparing(Estudiante::getNombres);		
		List<Estudiante> result = listaEstudiantes.stream()
				.filter(e -> e.getNombres().equalsIgnoreCase(nombres))
				.sorted(groupByComparator).collect(Collectors.toList());
		return result;		
	}
	
	public Estudiante obtenerEstudiante(int codigo) throws Exception{
		Optional<Estudiante> match = listaEstudiantes.stream().filter(e -> e.getCodigo()== codigo).findFirst();
		
		if(match.isPresent()) {
			return match.get();
			}else {
				throw new Exception("El estudiante con ID: "+codigo+" no fue encontrado");
			}
	}
	public int guardarEstudiante(Estudiante estudiante) {
		listaEstudiantes.add(estudiante);
		return estudiante.getCodigo();
	}
	public boolean actualizarEstudiante(Estudiante estudiante) {
		int idActualizar=0;
		Optional<Estudiante> estudianteEncontrado = listaEstudiantes.stream().filter(c ->c.getCodigo() == estudiante.getCodigo()).findFirst();		
		if(estudianteEncontrado.isPresent()) {
			idActualizar=listaEstudiantes.indexOf(estudianteEncontrado.get());
			listaEstudiantes.set(idActualizar, estudiante);
			return true;
		} else {
			return false;
		}
	}
	public boolean eliminarEstudiante(int codigo) {
		Predicate<Estudiante> estudiante = e -> e.getCodigo() == codigo;
		if(listaEstudiante.removeIf(estudiante)) {
			return true;
		}else {
			return false;
		}
	}
	
}
