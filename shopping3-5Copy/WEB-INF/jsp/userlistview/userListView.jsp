<%@ page contentType="text/html;charset=Shift_JIS"%>
<%@ include file="/WEB-INF/jsp/jsp_header.jsp"%>

<html>
<head>
<title>�i�ɔ�j�o�^���[�U�[�ꗗ�i�����o���֎~�j</title>
</head>

<SCRIPT type="text/javascript">
<!--
	var count;
	var b = 0;
	function CheckboxChecked(check) {
		if (document.C.c.length) {
			for (count = 0; count < document.C.c.length; count++) {
				document.C.c[count].checked = check;
			}
		} else {
			document.C.c.checked = check;
		}
	}

	function CheckboxBoolean() {
		if (b == 0) {
			b = 1;
			return true;
		} else {
			b = 0;
			return false;
		}
	}
// -->
</SCRIPT>
<noscript>JavaScript�Ή��u���E�U�ŕ\�����Ă��������B</noscript>


<body>
	<%@ include file="/WEB-INF/jsp/cart_header.jsp"%>
	<div align="center" class="body">
		<h2>�o�^���[�U�[�ꗗ</h2>
		<form:form  modelAttribute="user" method="POST"
			action="search.html">���[�UID����
<input type="text" name="userId" />
			<input type="submit" value="����" />
			<a href="../userentryform/userEntry.html">���[�U�o�^</a>
			<br><td><font color="red"><form:errors path="job" /></font></td>
		</form:form>
		<table border="1">
			<form:form  modelAttribute="aaa"  name = "C" method="POST"
				  action="delete.html">
				<tr class="header">
					<th align="center"><input type="submit"  value="delete"></th>
					<th align="center" width="70">���[�UID</th>
					<th align="center" width="50">���[�U��</th>
					<th align="center" width="50">password</th>
					<th align="center" width="140">�X�֔ԍ�</th>
					<th align="center" width="140">�Z��</th>
					<th align="center" width="70">E-Mail</th>
					<th align="center" width="25">�d��</th>
					<th align="center" width="50">�a����</th>
				</tr>
				<c:forEach items="${userList}" var="user" varStatus="id" >
					<tr class="record">
						<td align="right"><input type="checkbox" name="c" value ="${user.userId}"></td>
						<td align="right">${user.userId}</td>
						<td align="center">${user.userName}</td>
						<td align="right">${user.password}</td>
						<td align="left">��${user.postCode}</td>
						<td align="left">${user.address}</td>
						<td align="left">${user.email}</td>
						<td align="left">${user.job}</td>
						<td align="left">${user.strBirth}</td>
					</tr>
					${check.ids[id.index]}
				</c:forEach>
			</form:form>
		</table>
		<INPUT class="but" type="button"
			onClick="CheckboxChecked(CheckboxBoolean());" value="�ꊇ�I��">

	</div>
	<a href="../index/index.html">���ꗗ�ɖ߂�</a>
	<br>
</body>
</html>