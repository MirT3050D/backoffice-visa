<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<nav class="navbar">
    <div class="navbar-container">
        <div class="navbar-brand">
            <svg class="logo-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor">
                <path d="M12 1L3 5v6c0 5.55 3.84 10.74 9 12 5.16-1.26 9-6.45 9-12V5l-9-4z"/>
            </svg>
            <span>VISA SYSTEM</span>
        </div>
        <% 
            String requestURI = request.getRequestURI();
            if (!requestURI.endsWith("/")) {
        %>
            <a href="/" class="nav-back">Retour</a>
        <% } %>
    </div>
</nav>
