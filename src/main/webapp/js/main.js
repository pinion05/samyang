// Main JavaScript for Samyang Agricultural Platform

// DOM Ready
document.addEventListener('DOMContentLoaded', function() {
    // Initialize all modules
    initializeFormValidation();
    initializeTooltips();
    initializeModals();
    initializeAlerts();
    initializeImagePreviews();
    initializeCharCounters();
    initializeRatingSystem();
    initializeDataTables();
    initializeAjaxHandlers();
    initializeMobileMenu();
});

// Form Validation
function initializeFormValidation() {
    const forms = document.querySelectorAll('form[data-validate="true"]');
    
    forms.forEach(form => {
        form.addEventListener('submit', function(e) {
            if (!validateForm(this)) {
                e.preventDefault();
                showAlert('모든 필수 항목을 올바르게 입력해주세요.', 'error');
            }
        });
    });
}

function validateForm(form) {
    let isValid = true;
    const requiredFields = form.querySelectorAll('[required]');
    
    requiredFields.forEach(field => {
        if (!field.value.trim()) {
            isValid = false;
            showFieldError(field, '이 항목은 필수입니다.');
        } else {
            clearFieldError(field);
        }
        
        // Email validation
        if (field.type === 'email' && !isValidEmail(field.value)) {
            isValid = false;
            showFieldError(field, '올바른 이메일 형식이 아닙니다.');
        }
        
        // Phone validation
        if (field.type === 'tel' && !isValidPhone(field.value)) {
            isValid = false;
            showFieldError(field, '올바른 전화번호 형식이 아닙니다.');
        }
    });
    
    return isValid;
}

function showFieldError(field, message) {
    field.classList.add('is-invalid');
    let errorDiv = field.nextElementSibling;
    
    if (!errorDiv || !errorDiv.classList.contains('error-message')) {
        errorDiv = document.createElement('div');
        errorDiv.className = 'error-message text-danger small mt-1';
        field.parentNode.insertBefore(errorDiv, field.nextSibling);
    }
    
    errorDiv.textContent = message;
}

function clearFieldError(field) {
    field.classList.remove('is-invalid');
    const errorDiv = field.nextElementSibling;
    
    if (errorDiv && errorDiv.classList.contains('error-message')) {
        errorDiv.remove();
    }
}

function isValidEmail(email) {
    return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);
}

function isValidPhone(phone) {
    return /^[0-9]{2,3}-[0-9]{3,4}-[0-9]{4}$/.test(phone);
}

// Tooltips
function initializeTooltips() {
    const tooltips = document.querySelectorAll('[data-tooltip]');
    
    tooltips.forEach(element => {
        element.addEventListener('mouseenter', function() {
            const tooltip = document.createElement('div');
            tooltip.className = 'tooltip-popup';
            tooltip.textContent = this.getAttribute('data-tooltip');
            
            document.body.appendChild(tooltip);
            
            const rect = this.getBoundingClientRect();
            tooltip.style.top = rect.top - tooltip.offsetHeight - 10 + 'px';
            tooltip.style.left = rect.left + (rect.width - tooltip.offsetWidth) / 2 + 'px';
            
            this._tooltip = tooltip;
        });
        
        element.addEventListener('mouseleave', function() {
            if (this._tooltip) {
                this._tooltip.remove();
                delete this._tooltip;
            }
        });
    });
}

// Modals
function initializeModals() {
    // Open modal
    document.addEventListener('click', function(e) {
        if (e.target.matches('[data-modal-open]')) {
            const modalId = e.target.getAttribute('data-modal-open');
            const modal = document.getElementById(modalId);
            
            if (modal) {
                openModal(modal);
            }
        }
    });
    
    // Close modal
    document.addEventListener('click', function(e) {
        if (e.target.matches('.modal .close, [data-modal-close]')) {
            const modal = e.target.closest('.modal');
            if (modal) {
                closeModal(modal);
            }
        }
    });
    
    // Close modal on outside click
    document.addEventListener('click', function(e) {
        if (e.target.matches('.modal')) {
            closeModal(e.target);
        }
    });
}

function openModal(modal) {
    modal.style.display = 'block';
    document.body.style.overflow = 'hidden';
    
    setTimeout(() => {
        modal.classList.add('show');
    }, 10);
}

function closeModal(modal) {
    modal.classList.remove('show');
    
    setTimeout(() => {
        modal.style.display = 'none';
        document.body.style.overflow = '';
    }, 300);
}

// Alerts
function initializeAlerts() {
    // Auto dismiss alerts
    const alerts = document.querySelectorAll('.alert[data-auto-dismiss]');
    
    alerts.forEach(alert => {
        const dismissTime = parseInt(alert.getAttribute('data-auto-dismiss')) || 5000;
        
        setTimeout(() => {
            fadeOut(alert);
        }, dismissTime);
    });
    
    // Close button
    document.addEventListener('click', function(e) {
        if (e.target.matches('.alert .close')) {
            const alert = e.target.closest('.alert');
            fadeOut(alert);
        }
    });
}

function showAlert(message, type = 'info') {
    const alertContainer = document.getElementById('alert-container') || createAlertContainer();
    
    const alert = document.createElement('div');
    alert.className = `alert alert-${type}`;
    alert.innerHTML = `
        ${message}
        <button type="button" class="close">&times;</button>
    `;
    
    alertContainer.appendChild(alert);
    
    setTimeout(() => {
        fadeOut(alert);
    }, 5000);
}

function createAlertContainer() {
    const container = document.createElement('div');
    container.id = 'alert-container';
    container.style.cssText = 'position: fixed; top: 20px; right: 20px; z-index: 9999; max-width: 350px;';
    document.body.appendChild(container);
    return container;
}

function fadeOut(element) {
    element.style.opacity = '0';
    element.style.transform = 'translateX(100%)';
    
    setTimeout(() => {
        element.remove();
    }, 300);
}

// Image Previews
function initializeImagePreviews() {
    const fileInputs = document.querySelectorAll('input[type="file"][data-preview]');
    
    fileInputs.forEach(input => {
        input.addEventListener('change', function() {
            const previewId = this.getAttribute('data-preview');
            const preview = document.getElementById(previewId);
            
            if (preview && this.files && this.files[0]) {
                const reader = new FileReader();
                
                reader.onload = function(e) {
                    if (preview.tagName === 'IMG') {
                        preview.src = e.target.result;
                    } else {
                        preview.style.backgroundImage = `url(${e.target.result})`;
                    }
                };
                
                reader.readAsDataURL(this.files[0]);
            }
        });
    });
}

// Character Counters
function initializeCharCounters() {
    const textareas = document.querySelectorAll('textarea[maxlength]');
    
    textareas.forEach(textarea => {
        const maxLength = parseInt(textarea.getAttribute('maxlength'));
        const counter = document.createElement('div');
        counter.className = 'char-counter text-muted small mt-1';
        counter.textContent = `0 / ${maxLength}`;
        
        textarea.parentNode.insertBefore(counter, textarea.nextSibling);
        
        textarea.addEventListener('input', function() {
            const currentLength = this.value.length;
            counter.textContent = `${currentLength} / ${maxLength}`;
            
            if (currentLength > maxLength * 0.9) {
                counter.classList.add('text-danger');
            } else {
                counter.classList.remove('text-danger');
            }
        });
        
        // Trigger initial count
        textarea.dispatchEvent(new Event('input'));
    });
}

// Rating System
function initializeRatingSystem() {
    const ratingContainers = document.querySelectorAll('.rating-input');
    
    ratingContainers.forEach(container => {
        const stars = container.querySelectorAll('.star');
        const input = container.querySelector('input[type="hidden"]');
        
        stars.forEach((star, index) => {
            star.addEventListener('click', function() {
                const rating = index + 1;
                input.value = rating;
                
                stars.forEach((s, i) => {
                    if (i < rating) {
                        s.classList.add('active');
                        s.innerHTML = '★';
                    } else {
                        s.classList.remove('active');
                        s.innerHTML = '☆';
                    }
                });
            });
            
            star.addEventListener('mouseenter', function() {
                const hoverRating = index + 1;
                
                stars.forEach((s, i) => {
                    if (i < hoverRating) {
                        s.classList.add('hover');
                    } else {
                        s.classList.remove('hover');
                    }
                });
            });
        });
        
        container.addEventListener('mouseleave', function() {
            stars.forEach(s => s.classList.remove('hover'));
        });
    });
}

// Data Tables
function initializeDataTables() {
    const tables = document.querySelectorAll('.data-table');
    
    tables.forEach(table => {
        // Add sorting functionality
        const headers = table.querySelectorAll('th[data-sortable]');
        
        headers.forEach(header => {
            header.style.cursor = 'pointer';
            header.addEventListener('click', function() {
                const column = this.cellIndex;
                const currentOrder = this.getAttribute('data-order') || 'asc';
                const newOrder = currentOrder === 'asc' ? 'desc' : 'asc';
                
                sortTable(table, column, newOrder);
                this.setAttribute('data-order', newOrder);
                
                // Update sort indicators
                headers.forEach(h => h.classList.remove('sort-asc', 'sort-desc'));
                this.classList.add(`sort-${newOrder}`);
            });
        });
    });
}

function sortTable(table, column, order) {
    const tbody = table.querySelector('tbody');
    const rows = Array.from(tbody.querySelectorAll('tr'));
    
    rows.sort((a, b) => {
        const aValue = a.cells[column].textContent.trim();
        const bValue = b.cells[column].textContent.trim();
        
        if (order === 'asc') {
            return aValue.localeCompare(bValue, 'ko', { numeric: true });
        } else {
            return bValue.localeCompare(aValue, 'ko', { numeric: true });
        }
    });
    
    rows.forEach(row => tbody.appendChild(row));
}

// AJAX Handlers
function initializeAjaxHandlers() {
    // AJAX form submission
    const ajaxForms = document.querySelectorAll('form[data-ajax]');
    
    ajaxForms.forEach(form => {
        form.addEventListener('submit', function(e) {
            e.preventDefault();
            
            const formData = new FormData(this);
            const url = this.action;
            const method = this.method || 'POST';
            
            fetch(url, {
                method: method,
                body: formData
            })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    showAlert(data.message || '작업이 완료되었습니다.', 'success');
                    
                    if (data.redirect) {
                        setTimeout(() => {
                            window.location.href = data.redirect;
                        }, 1000);
                    }
                } else {
                    showAlert(data.message || '오류가 발생했습니다.', 'error');
                }
            })
            .catch(error => {
                showAlert('네트워크 오류가 발생했습니다.', 'error');
                console.error('AJAX Error:', error);
            });
        });
    });
    
    // AJAX delete buttons
    document.addEventListener('click', function(e) {
        if (e.target.matches('[data-ajax-delete]')) {
            e.preventDefault();
            
            if (confirm('정말로 삭제하시겠습니까?')) {
                const url = e.target.getAttribute('data-ajax-delete');
                
                fetch(url, {
                    method: 'DELETE'
                })
                .then(response => response.json())
                .then(data => {
                    if (data.success) {
                        showAlert('삭제되었습니다.', 'success');
                        
                        // Remove element from DOM
                        const targetId = e.target.getAttribute('data-target');
                        if (targetId) {
                            const element = document.getElementById(targetId);
                            if (element) {
                                fadeOut(element);
                            }
                        }
                    } else {
                        showAlert(data.message || '삭제 중 오류가 발생했습니다.', 'error');
                    }
                })
                .catch(error => {
                    showAlert('네트워크 오류가 발생했습니다.', 'error');
                    console.error('Delete Error:', error);
                });
            }
        }
    });
}

// Mobile Menu
function initializeMobileMenu() {
    const menuToggle = document.querySelector('.mobile-menu-toggle');
    const navMenu = document.querySelector('.nav-menu');
    
    if (menuToggle && navMenu) {
        menuToggle.addEventListener('click', function() {
            navMenu.classList.toggle('active');
            this.classList.toggle('active');
        });
        
        // Close menu on outside click
        document.addEventListener('click', function(e) {
            if (!menuToggle.contains(e.target) && !navMenu.contains(e.target)) {
                navMenu.classList.remove('active');
                menuToggle.classList.remove('active');
            }
        });
    }
}

// Utility Functions
function debounce(func, wait) {
    let timeout;
    return function executedFunction(...args) {
        const later = () => {
            clearTimeout(timeout);
            func(...args);
        };
        clearTimeout(timeout);
        timeout = setTimeout(later, wait);
    };
}

function throttle(func, limit) {
    let inThrottle;
    return function(...args) {
        if (!inThrottle) {
            func.apply(this, args);
            inThrottle = true;
            setTimeout(() => inThrottle = false, limit);
        }
    };
}

// Export functions for external use
window.SamyangApp = {
    showAlert,
    openModal,
    closeModal,
    validateForm,
    debounce,
    throttle
};