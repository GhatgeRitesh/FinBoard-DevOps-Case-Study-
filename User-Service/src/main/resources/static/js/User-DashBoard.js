 // API Configuration
    const API_BASE_URL = 'https://api.finboard.com/v1';
    const API_ENDPOINTS = {
        userInfo: '/user/profile',
        accountSummary: '/user/account-summary',
        transactions: '/user/transactions/recent',
        requests: '/user/requests',
        notifications: '/user/notifications',
        analytics: '/user/analytics'
    };

    // Mock data for demonstration (replace with actual API calls)
    const mockData = {
        userInfo: {
            name: 'John Doe',
            email: 'john.doe@email.com',
            role: 'USER',
            profession: 'Software Engineer',
            purpose: 'Personal Banking',
            status: 'active'
        },
        accountSummary: {
            balance: 25750.50,
            currency: 'USD',
            accountStatus: 'active',
            activeServices: ['Wallet', 'Insurance', 'Transfers'],
            kycStatus: 'completed'
        },
        transactions: [
            {
                id: 'TXN001',
                type: 'Credit',
                amount: 1500.00,
                description: 'Salary Credit',
                date: '2025-07-19',
                status: 'completed'
            },
            {
                id: 'TXN002',
                type: 'Debit',
                amount: -250.00,
                description: 'Online Purchase',
                date: '2025-07-18',
                status: 'completed'
            },
            {
                id: 'TXN003',
                type: 'Credit',
                amount: 75.00,
                description: 'Cashback Reward',
                date: '2025-07-17',
                status: 'completed'
            }
        ],
        requests: [
            {
                id: 'REQ001',
                type: 'Emergency Service',
                description: 'Medical Emergency Loan',
                status: 'pending',
                date: '2025-07-18'
            },
            {
                id: 'REQ002',
                type: 'Support Ticket',
                description: 'Transaction Dispute',
                status: 'resolved',
                date: '2025-07-15'
            }
        ],
        notifications: [
            {
                id: 'NOT001',
                title: 'Transaction Success',
                message: 'Your payment of $250 was successful',
                date: '2025-07-19',
                type: 'success'
            },
            {
                id: 'NOT002',
                title: 'New Service Available',
                message: 'Check out our new investment plans',
                date: '2025-07-18',
                type: 'info'
            }
        ],
        analytics: {
            spendingTrend: 85,
            serviceUsage: 72,
            kycProgress: 100
        }
    };

    // Utility function to make API calls
    async function apiCall(endpoint, method = 'GET', data = null) {
        try {
            const config = {
                method,
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${localStorage.getItem('authToken')}`
                }
            };

            if (data && method !== 'GET') {
                config.body = JSON.stringify(data);
            }

            const response = await fetch(`${API_BASE_URL}${endpoint}`, config);

            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }

            return await response.json();
        } catch (error) {
            console.error('API call failed:', error);
            // Return mock data for demonstration
            return getMockData(endpoint);
        }
    }

    // Get mock data based on endpoint
    function getMockData(endpoint) {
        switch (endpoint) {
            case API_ENDPOINTS.userInfo:
                return mockData.userInfo;
            case API_ENDPOINTS.accountSummary:
                return mockData.accountSummary;
            case API_ENDPOINTS.transactions:
                return mockData.transactions;
            case API_ENDPOINTS.requests:
                return mockData.requests;
            case API_ENDPOINTS.notifications:
                return mockData.notifications;
            case API_ENDPOINTS.analytics:
                return mockData.analytics;
            default:
                return null;
        }
    }

    // Load user information
    async function loadUserInfo() {
        try {
            const userInfo = await apiCall(API_ENDPOINTS.userInfo);

            document.getElementById('userName').textContent = userInfo.name;

            const userInfoHTML = `
                <div class="user-field">
                    <span class="field-label">Name:</span>
                    <span class="field-value">${userInfo.name}</span>
                </div>
                <div class="user-field">
                    <span class="field-label">Email:</span>
                    <span class="field-value">${userInfo.email}</span>
                </div>
                <div class="user-field">
                    <span class="field-label">Role:</span>
                    <span class="field-value">${userInfo.role}</span>
                </div>
                <div class="user-field">
                    <span class="field-label">Profession:</span>
                    <span class="field-value">${userInfo.profession || 'Not specified'}</span>
                </div>
                <div class="user-field">
                    <span class="field-label">Purpose:</span>
                    <span class="field-value">${userInfo.purpose}</span>
                </div>
                <div class="user-field">
                    <span class="field-label">Status:</span>
                    <span class="status-badge status-${userInfo.status}">${userInfo.status}</span>
                </div>
            `;

            document.getElementById('userInfo').innerHTML = userInfoHTML;
        } catch (error) {
            document.getElementById('userInfo').innerHTML = '<div class="error-message">Failed to load user information</div>';
        }
    }

    // Load account summary
    async function loadAccountSummary() {
        try {
            const accountData = await apiCall(API_ENDPOINTS.accountSummary);

            const summaryHTML = `
                <div class="balance-display">
                    <div class="balance-amount">$${accountData.balance.toLocaleString()}</div>
                    <div class="balance-label">Current Balance</div>
                </div>
                <div class="user-field">
                    <span class="field-label">Account Status:</span>
                    <span class="status-badge status-${accountData.accountStatus}">${accountData.accountStatus}</span>
                </div>
                <div class="user-field">
                    <span class="field-label">Active Services:</span>
                    <span class="field-value">${accountData.activeServices.join(', ')}</span>
                </div>
                <div class="user-field">
                    <span class="field-label">KYC Status:</span>
                    <span class="status-badge status-active">${accountData.kycStatus}</span>
                </div>
            `;

            document.getElementById('accountSummary').innerHTML = summaryHTML;
        } catch (error) {
            document.getElementById('accountSummary').innerHTML = '<div class="error-message">Failed to load account summary</div>';
        }
    }

    // Load recent transactions
    async function loadTransactions() {
        try {
            const transactions = await apiCall(API_ENDPOINTS.transactions);

            const transactionsHTML = transactions.map(txn => `
                <div class="transaction-item">
                    <div class="transaction-info">
                        <h4>${txn.description}</h4>
                        <div class="transaction-date">${new Date(txn.date).toLocaleDateString()}</div>
                    </div>
                    <div class="transaction-amount ${txn.amount > 0 ? 'amount-positive' : 'amount-negative'}">
                        ${txn.amount > 0 ? '+' : ''}$${Math.abs(txn.amount).toFixed(2)}
                    </div>
                </div>
            `).join('');

            document.getElementById('transactions').innerHTML = transactionsHTML || '<div class="error-message">No transactions found</div>';
        } catch (error) {
            document.getElementById('transactions').innerHTML = '<div class="error-message">Failed to load transactions</div>';
        }
    }

    // Load requests
    async function loadRequests() {
        try {
            const requests = await apiCall(API_ENDPOINTS.requests);

            const requestsHTML = requests.map(req => `
                <div class="request-item">
                    <div class="request-info">
                        <h4>${req.description}</h4>
                        <div class="request-date">${new Date(req.date).toLocaleDateString()}</div>
                    </div>
                    <span class="status-badge status-${req.status}">${req.status}</span>
                </div>
            `).join('');

            document.getElementById('requests').innerHTML = requestsHTML || '<div class="error-message">No requests found</div>';
        } catch (error) {
            document.getElementById('requests').innerHTML = '<div class="error-message">Failed to load requests</div>';
        }
    }

    // Load notifications
    async function loadNotifications() {
        try {
            const notifications = await apiCall(API_ENDPOINTS.notifications);

            const notificationsHTML = notifications.map(notif => `
                <div class="notification-item">
                    <div class="notification-info">
                        <h4>${notif.title}</h4>
                        <div class="notification-date">${new Date(notif.date).toLocaleDateString()}</div>
                    </div>
                </div>
            `).join('');

            document.getElementById('notifications').innerHTML = notificationsHTML || '<div class="error-message">No notifications found</div>';
        } catch (error) {
            document.getElementById('notifications').innerHTML = '<div class="error-message">Failed to load notifications</div>';
        }
    }

    // Load analytics
    async function loadAnalytics() {
        try {
            const analytics = await apiCall(API_ENDPOINTS.analytics);

            const analyticsHTML = `
                <div class="user-field">
                    <span class="field-label">Spending Trend:</span>
                    <div class="progress-bar">
                        <div class="progress-fill" style="width: ${analytics.spendingTrend}%"></div>
                    </div>
                </div>
                <div class="user-field">
                    <span class="field-label">Service Usage:</span>
                    <div class="progress-bar">
                        <div class="progress-fill" style="width: ${analytics.serviceUsage}%"></div>
                    </div>
                </div>
                <div class="user-field">
                    <span class="field-label">KYC Progress:</span>
                    <div class="progress-bar">
                        <div class="progress-fill" style="width: ${analytics.kycProgress}%"></div>
                    </div>
                </div>
            `;

            document.getElementById('analytics').innerHTML = analyticsHTML;
        } catch (error) {
            document.getElementById('analytics').innerHTML = '<div class="error-message">Failed to load analytics</div>';
        }
    }

    // Action functions
    function requestService() {
        alert('Request Service functionality - Redirect to service request page');
        // window.location.href = '/request-service';
    }

    function makePayment() {
        alert('Make Payment functionality - Redirect to payment page');
        // window.location.href = '/make-payment';
    }

    function raiseTicket() {
        alert('Raise Ticket functionality - Redirect to support page');
        // window.location.href = '/support/new-ticket';
    }

    function downloadStatement() {
        alert('Download Statement functionality - Generate and download PDF');
        // Implementation for statement download
    }

    function editProfile() {
        alert('Edit Profile functionality - Redirect to profile settings');
        // window.location.href = '/profile/settings';
    }

    function changePassword() {
        alert('Change Password functionality - Redirect to password change page');
        // window.location.href = '/profile/change-password';
    }

    function viewAllTransactions() {
        alert('View All Transactions functionality');
        // window.location.href = '/transactions';
    }

    function openChatbot() {
        alert('Opening Chatbot - Integration with Gemini or other chatbot service');
        // Implement chatbot integration
    }

    function logout() {
        if (confirm('Are you sure you want to logout?')) {
            localStorage.removeItem('authToken');
            alert('Logged out successfully');
            // window.location.href = '/login';
        }
    }

    // Initialize dashboard
    async function initializeDashboard() {
        await Promise.all([
            loadUserInfo(),
            loadAccountSummary(),
            loadTransactions(),
            loadRequests(),
            loadNotifications(),
            loadAnalytics()
        ]);
    }

    // Load dashboard data when page loads
    document.addEventListener('DOMContentLoaded', initializeDashboard);