<%@ page session="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Tester Page</title>

<style type="text/css">
h2 {
	color: #000099;
	text-decoration:underline;
}

.wrapper {
	margin: 0 auto;
	width: 95%;
}

.yes {
	color: green;
	font-weight: bold;
}

.no {
	color: #CC0000 font-weight:   bold;
}

.summary {
	float: left;
	width: 45%;
	margin: 10px;
}

.memory {
	float: left;
	width: 100%;
	margin: 10px;
}

.servicePing {
	float: right;
	width: 35%;
	margin: 10px;
}

.catalog {
	float: left;
	width: 100%;
	margin: 10px;
}

.catalogItem {
	font-size: 0.7em;
}

.two-edge-shadow {
	-webkit-box-shadow: 3px 8px 6px -6px black;
	-moz-box-shadow: 3px 8px 6px -6px black;
	box-shadow: 3px 8px 6px -6px black;
}
</style>

</head>
<body>

	<div class="wrapper">

		<div class="summary">
			<h2>
				<span class="yes">HostServices application up and running</span>
			</h2>
			Time on server: ${serverTime}.<br /> 
			Locale on server: ${locale}.<br />
			
			<h2>Build Info</h2>
			<b>Repository:</b> ${build_repository}.<br />
			<b>Path:</b> ${build_path}.<br />
			<b>Revision:</b> ${build_revision}.<br />
			<b>Mixed Revisions?:</b> ${build_mixedRevisions}.<br />
			<b>Committed Revision:</b> ${build_committedRevision}.<br />
			<b>Committed Date:</b> ${build_committedDate}.<br />
			<b>Status:</b> ${build_specialStatus}.<br />
		</div>

		<div class="servicePing two-edge-shadow">
			<h2>Service ping tests</h2>

			<h3>HTTP Get</h3>
			Ping path : ${getPingPath}.<br />
			
			Remote Host : ${myIP}.<br />

			<c:if test="${getPingSuccess}">
				Ping success? : <span class="yes">YES</span>
				<br />
			</c:if>
			<c:if test="${!getPingSuccess}">
				Ping success? : <span class="no">NO</span>
				<br />
			</c:if>

			Time taken? : ${getPingTimeTaken}.<br />

			<h3>HTTP Post</h3>

			Ping path : ${postPingPath}.<br />
			<c:if test="${postPingSuccess}">
				Ping success? : <span class="yes">YES</span>
				<br />
			</c:if>
			<c:if test="${!postPingSuccess}">
				Ping success? : <span class="no">NO</span>
				<br />
			</c:if>
			Ping time taken? : ${postPingTimeTaken}.<br />
		</div>
		
		<div class="memory two-edge-shadow">
			<h2>Memory Statistics</h2>
			Used Memory : ${usedmemory} mb<br />
			Free Memory : ${freememory} mb<br />
			Allocated Memory : ${allocatedmemory} mb<br />
			Max Memory : ${maxmemory} mb<br />

			<h4>JMX Memory Statistics [Now](Peak)</h4>
			<c:forEach items="${jmx}" var="element">
				${element}<br />
			</c:forEach>
		</div>
		
		<div class="memory two-edge-shadow">
			<h2>Environment Variables</h2>

			<c:forEach items="${envvars}" var="element">
				${element}<br />
			</c:forEach>
		</div>

		<div class="catalog two-edge-shadow">
			<h2>Service catalog</h2>
			<c:set var="count" value="1" scope="page" />
			<c:forEach items="${catalog}" var="element">
				<span class="catalogItem">${count} - ${element}</span>
				<br />
				<c:set var="count" value="${count + 1}" scope="page" />
			</c:forEach>
		</div>

	</div>

</body>
</html>
