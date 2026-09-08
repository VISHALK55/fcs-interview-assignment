# Case Study Scenarios to discuss

## Scenario 1: Cost Allocation and Tracking
**Situation**: The company needs to track and allocate costs accurately across different Warehouses and Stores. The costs include labor, inventory, transportation, and overhead expenses.

**Task**: Discuss the challenges in accurately tracking and allocating costs in a fulfillment environment. Think about what are important considerations for this, what are previous experiences that you have you could related to this problem and elaborate some questions and considerations

**Questions you may have and considerations:**
**Considerations:**
- **Granularity of tracking:** Should costs be tracked at the aggregate level (e.g., total warehouse cost) or granular level (e.g., per SKU or per order line)?
- **Overhead allocation:** How do we distribute shared costs (like corporate rent or software licenses) across individual fulfillment units? Common practices involve using metrics like square footage, shipped volume, or headcount.
- **Data silos:** Labor data might come from HR systems, inventory from WMS (Warehouse Management System), and transportation from TMS (Transportation Management System), requiring a unified data warehouse.
**Questions:**
- Are our current systems capable of emitting cost data in real-time or near-real-time?
- What is the accepted margin of error for indirect cost allocations?

## Scenario 2: Cost Optimization Strategies
**Situation**: The company wants to identify and implement cost optimization strategies for its fulfillment operations. The goal is to reduce overall costs without compromising service quality.

**Task**: Discuss potential cost optimization strategies for fulfillment operations and expected outcomes from that. How would you identify, prioritize and implement these strategies?

**Questions you may have and considerations:**
**Considerations:**
- **Strategies:** Optimizing warehouse layout to reduce picking times (labor optimization), consolidating shipments (transportation), and reducing holding costs by improving demand forecasting (inventory optimization).
- **Prioritization:** Strategies should be evaluated using an Effort vs. Impact matrix. High-impact, low-effort changes (e.g., renegotiating carrier contracts) should be prioritized first.
- **Outcomes:** A more streamlined operation, improved delivery SLAs, and reduced operational expenditure (OpEx).
**Questions:**
- Do we currently have enough historical data to identify bottlenecks and validate optimization assumptions?
- Are there any strict service-level agreements (SLAs) with Stores that cannot be adjusted during optimization experiments?

## Scenario 3: Integration with Financial Systems
**Situation**: The Cost Control Tool needs to integrate with existing financial systems to ensure accurate and timely cost data. The integration should support real-time data synchronization and reporting.

**Task**: Discuss the importance of integrating the Cost Control Tool with financial systems. What benefits the company would have from that and how would you ensure seamless integration and data synchronization?

**Questions you may have and considerations:**
**Considerations:**
- **Importance:** Financial systems require accurate records for compliance, audits, and P&L (Profit & Loss) statements. Integration avoids manual reconciliation, reducing errors and saving time.
- **Benefits:** Real-time visibility into the financial health of fulfillment operations, enabling agile decision-making and preventing budget overruns.
- **Integration Approach:** Use an event-driven architecture (like Kafka) or robust API gateways to ensure data flows securely and reliably. Implement idempotency to prevent double-counting.
**Questions:**
- Which financial systems (e.g., SAP, Oracle ERP) are we integrating with, and do they support modern REST/GraphQL or event-driven patterns?
- What are the compliance and data security requirements (e.g., SOX compliance) for the data in transit?

## Scenario 4: Budgeting and Forecasting
**Situation**: The company needs to develop budgeting and forecasting capabilities for its fulfillment operations. The goal is to predict future costs and allocate resources effectively.

**Task**: Discuss the importance of budgeting and forecasting in fulfillment operations and what would you take into account designing a system to support accurate budgeting and forecasting?

**Questions you may have and considerations:**
**Considerations:**
- **Importance:** Fulfillment experiences extreme seasonality (e.g., Black Friday, holidays). Without forecasting, we risk severe understaffing (causing delays) or overstaffing (causing financial waste).
- **System Design:** The system should ingest historical data, market trends, and marketing plans. It should probably leverage Machine Learning for predictive analytics rather than relying solely on static formulas.
- **Factors:** Anticipated inflation, changing fuel costs for transportation, and lead times for inventory replenishment.
**Questions:**
- What historical data sets are available to train our forecasting models?
- How far out do we need to forecast (e.g., quarterly, annually), and how frequently will the forecasts be recalibrated?

## Scenario 5: Cost Control in Warehouse Replacement
**Situation**: The company is planning to replace an existing Warehouse with a new one. The new Warehouse will reuse the Business Unit Code of the old Warehouse. The old Warehouse will be archived, but its cost history must be preserved.

**Task**: Discuss the cost control aspects of replacing a Warehouse. Why is it important to preserve cost history and how this relates to keeping the new Warehouse operation within budget?

**Questions you may have and considerations:**
**Considerations:**
- **Preserving History:** Cost history is the baseline against which the new warehouse's efficiency will be judged. If the new warehouse is an upgrade (e.g., automated sorting), we need the old data to calculate the Return on Investment (ROI).
- **Cost Aspects:** The transition phase involves parallel costs (running the old warehouse while ramping up the new one, moving stock). This "double-paying" period must be tightly controlled.
- **Budgeting:** The old warehouse's metrics (cost per unit shipped, labor per unit) provide a budget ceiling that the new warehouse should ideally beat once fully operational.
**Questions:**
- How long will the overlapping transition period last, and is there a dedicated transition budget?
- Does the archiving process lock the financial records to prevent accidental modification, ensuring data integrity for future audits?

## Instructions for Candidates
Before starting the case study, read the [BRIEFING.md](BRIEFING.md) to quickly understand the domain, entities, business rules, and other relevant details.

**Analyze the Scenarios**: Carefully analyze each scenario and consider the tasks provided. To make informed decisions about the project's scope and ensure valuable outcomes, what key information would you seek to gather before defining the boundaries of the work? Your goal is to bridge technical aspects with business value, bringing a high level discussion; no need to deep dive.
