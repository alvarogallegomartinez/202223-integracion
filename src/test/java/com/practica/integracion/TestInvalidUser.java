package com.practica.integracion;

import com.practica.integracion.DAO.AuthDAO;
import com.practica.integracion.DAO.GenericDAO;
import com.practica.integracion.DAO.User;
import com.practica.integracion.manager.SystemManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TestInvalidUser {
	@Mock
	private AuthDAO mockAuthDao;
	@Mock
	private GenericDAO mockGenericDao;

	@Test
	public void testStartRemoteSystemWithInvalidUserAndSystem() throws Exception {
		User validUser = new User("1", "Ana", "Lopez", "Madrid", new ArrayList<>(Arrays.asList(1, 2)));
		when(mockAuthDao.getAuthData(validUser.getId())).thenReturn(validUser);

		String remoteId = "12345";
		ArrayList<Object> lista = new ArrayList<>(Arrays.asList("uno", "dos"));
		when(mockGenericDao.getSomeData(validUser, "where id=" + remoteId)).thenReturn(lista);

		InOrder ordered = inOrder(mockAuthDao, mockGenericDao);
		SystemManager manager = new SystemManager(mockAuthDao, mockGenericDao);

		Collection<Object> retorno = manager.startRemoteSystem(validUser.getId(), remoteId);
		assertEquals(retorno.toString(), "[uno, dos]");
		ordered.verify(mockAuthDao).getAuthData(validUser.getId());
		ordered.verify(mockGenericDao).getSomeData(validUser, "where id=" + remoteId);
	}

	@Test
	public void testStopRemoteSystemWithInvalidUserAndSystem() throws Exception {

	}

	@Test
	public void testAddRemoteSystemWithInvalidUserAndSystem() throws Exception {

	}

	@Test
	public void testDeleteRemoteSystemWithInvalidUserAndSystem() throws Exception {

	}
}
